package com.demo.project.bankissuerservice.service.impl;

import com.demo.project.bankissuerservice.dto.response.BankInfoDtoResponse;
import com.demo.project.bankissuerservice.exception.BankNotFoundException;
import com.demo.project.bankissuerservice.mapper.CardBinMapper;
import com.demo.project.bankissuerservice.repository.CardBinInfoRepository;
import com.demo.project.bankissuerservice.service.CardBinLookupService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
public class CardBinLookupServiceImpl implements CardBinLookupService {

    private CardBinInfoRepository repository;
    private CardBinMapper cardBinMapper;

    @Value("${card-bin-service.card-bin-lookup-service.card-padding-zeros}")
    private String cardPaddingZeros;

    @Override
    public BankInfoDtoResponse findBankInfoByCardNumber(String cardNumber) {
        var cardPadded = cardNumber + cardPaddingZeros.substring(cardNumber.length());
        return repository.findByMinRangeLessThanEqualAndMaxRangeGreaterThanEqual(cardPadded,
                cardPadded)
            .map(cardBinMapper::toCardBinInfoDtoResponse)
            .orElseThrow(
                () -> new BankNotFoundException(
                    String.format("No bank info found for card number: %s", cardNumber)));
    }
}
