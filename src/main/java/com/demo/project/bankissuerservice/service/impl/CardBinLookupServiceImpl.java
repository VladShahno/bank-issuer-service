package com.demo.project.bankissuerservice.service.impl;

import com.demo.project.bankissuerservice.dto.response.BankInfoDtoResponse;
import com.demo.project.bankissuerservice.exception.BankNotFoundException;
import com.demo.project.bankissuerservice.mapper.CardBinMapper;
import com.demo.project.bankissuerservice.repository.CardBinInfoRepository;
import com.demo.project.bankissuerservice.service.CardBinLookupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CardBinLookupServiceImpl implements CardBinLookupService {

    private final CardBinInfoRepository repository;
    private final CardBinMapper cardBinMapper;

    @Value("${card-bin-lookup-service.card-padding-zeros}")
    private String cardPaddingZeros;

    public CardBinLookupServiceImpl(CardBinInfoRepository repository, CardBinMapper cardBinMapper) {
        this.repository = repository;
        this.cardBinMapper = cardBinMapper;
    }

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
