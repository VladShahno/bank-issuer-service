package com.demo.project.bankissuerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "card_bin_info")
public class CardBinInfo {

    @Id
    private String bin;

    @JsonProperty("min_range")
    @Column(name = "min_range")
    private String minRange;

    @JsonProperty("max_range")
    @Column(name = "max_range")
    private String maxRange;

    @JsonProperty("alpha_code")
    @Column(name = "alpha_code")
    private String alphaCode;

    @JsonProperty("bank_name")
    @Column(name = "bank_name")
    private String bankName;
}
