package com.demo.project.bankissuerservice.service;

import com.demo.project.bankissuerservice.dto.response.BankInfoDtoResponse;

public interface CardBinLookupService {

    BankInfoDtoResponse findBankInfoByCardNumber(String cardNumber);

}
