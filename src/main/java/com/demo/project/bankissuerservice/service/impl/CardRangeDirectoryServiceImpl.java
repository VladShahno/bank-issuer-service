package com.demo.project.bankissuerservice.service.impl;

import com.demo.project.bankissuerservice.exception.CardBinInfoUpdateException;
import com.demo.project.bankissuerservice.model.CardBinInfo;
import com.demo.project.bankissuerservice.repository.CardBinInfoRepository;
import com.demo.project.bankissuerservice.service.CardRangeDirectoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
@Slf4j
public class CardRangeDirectoryServiceImpl implements CardRangeDirectoryService {

    private final CardBinInfoRepository repository;

    private final JdbcTemplate jdbcTemplate;

    @Value("${card-bin-service.bin-info-source-url}")
    private String binInfoResourceUrl;

    @Value("${card-bin-service.batch-size}")
    private int batchSize;

    public CardRangeDirectoryServiceImpl(CardBinInfoRepository repository, JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Scheduled(cron = "${card-bin-service.schedular.update-card-bin-info}")
    @Override
    public void updateCardRangeDirectory() {
        try (InputStream inputStream = new URL(binInfoResourceUrl).openStream();
            var zipInputStream = new ZipInputStream(inputStream)) {

            if (!processZipEntry(zipInputStream)) {
                log.warn("No entries found in the zip file.");
            }
        } catch (IOException e) {
            log.error("Failed to update card range directory: ", e);
            throw new CardBinInfoUpdateException(e.getMessage());
        }
    }

    private boolean processZipEntry(ZipInputStream zipInputStream) throws IOException {
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        if (Objects.nonNull(zipEntry)) {
            List<CardBinInfo> newCardBinInfoList = parseCardBinInfo(zipInputStream);
            if (newCardBinInfoList.isEmpty()) {
                log.warn("Received empty CardBinInfo data from the source.");
                return false;
            }

            executeDatabaseUpdate(newCardBinInfoList);
            return true;
        }
        return false;
    }

    private List<CardBinInfo> parseCardBinInfo(ZipInputStream zipInputStream) throws IOException {
        var mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(zipInputStream, CardBinInfo[].class));
    }

    private void executeDatabaseUpdate(List<CardBinInfo> newCardBinInfoList) {
        var stopWatch = new StopWatch();

        repository.createNewTableForBackup();
        stopWatch.start();

        batchInsertCardBinInfo(newCardBinInfoList);
        stopWatch.stop();

        switchTables();
        repository.dropOldTable();

        log.info("Successfully updated card range directory in {} ms",
            stopWatch.getTotalTimeMillis());
    }

    private void batchInsertCardBinInfo(List<CardBinInfo> cardBinInfoList) {
        String insertSql = "INSERT INTO card_bin_info_new (bin, min_range, max_range, alpha_code, bank_name) VALUES (?, ?, ?, ?, ?)";
        List<List<CardBinInfo>> partitions = partitionList(cardBinInfoList, batchSize);

        for (List<CardBinInfo> batch : partitions) {
            jdbcTemplate.batchUpdate(insertSql, batch, batch.size(),
                (preparedStatement, cardBinInfo) -> {
                    preparedStatement.setString(1, cardBinInfo.getBin());
                    preparedStatement.setString(2, cardBinInfo.getMinRange());
                    preparedStatement.setString(3, cardBinInfo.getMaxRange());
                    preparedStatement.setString(4, cardBinInfo.getAlphaCode());
                    preparedStatement.setString(5, cardBinInfo.getBankName());
                });

            log.info("Inserted batch of size {}.", batch.size());
        }
    }

    private void switchTables() {
        repository.renameOldTable();
        repository.renameNewTable();
    }

    private <T> List<List<T>> partitionList(List<T> list, int size) {
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }
}
