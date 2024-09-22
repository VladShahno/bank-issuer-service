package com.demo.project.bankissuerservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.demo.project.bankissuerservice.dto.response.BankInfoDtoResponse;
import com.demo.project.bankissuerservice.exception.BankNotFoundException;
import com.demo.project.bankissuerservice.mapper.CardBinMapper;
import com.demo.project.bankissuerservice.entity.CardBinInfo;
import com.demo.project.bankissuerservice.repository.CardBinInfoRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class IssuingBankSearchServiceImplTest {

    @Mock
    private CardBinInfoRepository repository;

    @Mock
    private CardBinMapper cardBinMapper;

    @InjectMocks
    private IssuingBankSearchServiceImpl issuingBankSearchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnBankInfoWhenCardIsValid() {
        String cardNumber = "4441110000004100000";
        String cardBin = "444111";
        CardBinInfo cardBinInfo = new CardBinInfo();
        cardBinInfo.setMinRange("4441110000000000");
        cardBinInfo.setMaxRange("4441119999999999");

        when(repository.findAllByBin(cardBin)).thenReturn(List.of(cardBinInfo));
        var expectedResponse = new BankInfoDtoResponse();
        when(cardBinMapper.toCardBinInfoDtoResponse(cardBinInfo)).thenReturn(expectedResponse);

        var result = issuingBankSearchService.findIssuingBankInfo(cardNumber);

        assertEquals(expectedResponse, result);
    }

    @Test
    void shouldThrowExceptionWhenNoBankFound() {
        String cardNumber = "1234560000000000";
        String cardBin = "123456";

        when(repository.findAllByBin(cardBin)).thenReturn(List.of());

        assertThrows(BankNotFoundException.class,
            () -> issuingBankSearchService.findIssuingBankInfo(cardNumber));
    }
}
