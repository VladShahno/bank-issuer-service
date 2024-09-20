package com.demo.project.bankissuerservice.dto.request;

import static com.demo.project.bankissuerservice.util.common.Constants.Validation.CARD_CAN_NOT_BE_EMPTY;
import static com.demo.project.bankissuerservice.util.common.Constants.Validation.CARD_REGEX;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BankInfoDtoRequest {

    @NotEmpty(message = CARD_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = CARD_REGEX)
    private String card;
}
