package com.demo.project.bankissuerservice.service.impl;

import com.demo.project.bankissuerservice.dto.response.BankInfoDtoResponse;
import com.demo.project.bankissuerservice.exception.BankNotFoundException;
import com.demo.project.bankissuerservice.mapper.CardBinMapper;
import com.demo.project.bankissuerservice.repository.CardBinInfoRepository;
import com.demo.project.bankissuerservice.service.IssuingBankSearchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class IssuingBankSearchServiceImpl implements IssuingBankSearchService {

    private final CardBinInfoRepository repository;
    private final CardBinMapper cardBinMapper;

    @Override
    public BankInfoDtoResponse findIssuingBankInfo(String cardNumber) {
        String cardBin = cardNumber.substring(0, 6);

        String cardForSearch = cardNumber.replaceAll("\\*", "0") + "000";

        return repository.findAllByBin(cardBin)
            .stream()
            .filter(cardBinInfo -> isCardNumberWithinRange(cardForSearch, cardBinInfo.getMinRange(),
                cardBinInfo.getMaxRange()))
            .findFirst()
            .map(cardBinMapper::toCardBinInfoDtoResponse)
            .orElseThrow(
                () -> new BankNotFoundException(
                    String.format("No bank info found for card number: %s", cardNumber)));
    }

    private boolean isCardNumberWithinRange(String cardNumber, String minRange, String maxRange) {
        log.debug("Checks for cardNumber: {} within minRange: {} and maxRange: {}", cardNumber,
            minRange, maxRange);
        return cardNumber.compareTo(minRange) >= 0 && cardNumber.compareTo(maxRange) <= 0;
    }
}
