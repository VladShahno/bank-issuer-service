package com.demo.project.bankissuerservice.controller;

import com.demo.project.bankissuerservice.dto.request.BankInfoDtoRequest;
import com.demo.project.bankissuerservice.dto.response.BankInfoDtoResponse;
import com.demo.project.bankissuerservice.service.IssuingBankSearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bin")
@AllArgsConstructor
@Validated
public class BinController {

    private IssuingBankSearchService issuingBankSearchService;

    @PostMapping
    public ResponseEntity<BankInfoDtoResponse> getIssuingBankInfo(
        @RequestBody BankInfoDtoRequest request) {

        return ResponseEntity.ok(issuingBankSearchService.findIssuingBankInfo(request.getCard()));
    }
}
