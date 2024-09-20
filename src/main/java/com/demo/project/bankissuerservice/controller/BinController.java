package com.demo.project.bankissuerservice.controller;

import static com.demo.project.bankissuerservice.util.CardUtil.cleanCardNumber;

import com.demo.project.bankissuerservice.dto.request.BankInfoDtoRequest;
import com.demo.project.bankissuerservice.dto.response.BankInfoDtoResponse;
import com.demo.project.bankissuerservice.service.CardBinLookupService;
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

    private CardBinLookupService cardBinLookupService;

    @PostMapping
    public ResponseEntity<BankInfoDtoResponse> getCardBinInfo(
        @RequestBody BankInfoDtoRequest request) {
        String cleanedCardNumber = cleanCardNumber(request.getCard());
        return ResponseEntity.ok(cardBinLookupService.findBankInfoByCardNumber(cleanedCardNumber));
    }
}
