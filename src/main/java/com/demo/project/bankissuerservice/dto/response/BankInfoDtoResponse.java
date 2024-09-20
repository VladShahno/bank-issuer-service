package com.demo.project.bankissuerservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BankInfoDtoResponse {

    @JsonProperty(value = "bin")
    private String bin;

    @JsonProperty(value = "alphacode")
    private String alphaCode;

    @JsonProperty(value = "name")
    private String bankName;
}
