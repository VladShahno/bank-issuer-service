package com.demo.project.bankissuerservice.model;

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

    @Column(name = "min_range", nullable = false)
    private String minRange;

    @Column(name = "max_range", nullable = false)
    private String maxRange;

    @Column(name = "alpha_code", nullable = false)
    private String alphaCode;

    @Column(name = "bank_name", nullable = false)
    private String bankName;
}
