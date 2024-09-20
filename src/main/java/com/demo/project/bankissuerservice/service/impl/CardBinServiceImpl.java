package com.demo.project.bankissuerservice.service.impl;

import com.demo.project.bankissuerservice.exception.CardBinInfoUpdateException;
import com.demo.project.bankissuerservice.model.CardBinInfo;
import com.demo.project.bankissuerservice.repository.CardBinInfoRepository;
import com.demo.project.bankissuerservice.service.CardBinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CardBinServiceImpl implements CardBinService {

    private final CardBinInfoRepository repository;

    @Value("${card-bin-service.bin-info-source-url}")
    private String binInfoResourceUrl;

    public CardBinServiceImpl(CardBinInfoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Scheduled(cron = "${card-bin-service.schedular.update-card-bin-info}")
    @Override
    public void updateCardBinInfo() {
        try (InputStream inputStream = new URL(binInfoResourceUrl).openStream();
            var zipInputStream = new ZipInputStream(inputStream)) {

            ZipEntry zipEntry = zipInputStream.getNextEntry();
            if (Objects.nonNull(zipEntry)) {
                var mapper = new ObjectMapper();
                var newCardBinIfo = Arrays.asList(
                    mapper.readValue(zipInputStream, CardBinInfo[].class));

                // Validate new data
                if (newCardBinIfo.isEmpty()) {
                    log.warn("Received empty bin data from the source.");
                    return;
                }

                // Backup old data
                List<CardBinInfo> oldBinData = repository.findAll();
                if (!oldBinData.isEmpty()) {
                    repository.saveAll(
                        oldBinData);  //TODO saving to a backup table or another mechanism
                }

                repository.deleteAll();
                repository.saveAll(newCardBinIfo);
                log.debug("Successfully updated card bin information.");
            } else {
                log.warn("No entries found in the zip file.");
            }
        } catch (IOException e) {
            log.error("Failed to update card bin information: ", e);
            throw new CardBinInfoUpdateException(e.getMessage());
        }
    }
}
