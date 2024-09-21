package com.demo.project.bankissuerservice.service;

import com.demo.project.bankissuerservice.dto.response.BankInfoDtoResponse;

public interface IssuingBankSearchService {

    BankInfoDtoResponse findIssuingBankInfo(String cardNumber);
}
