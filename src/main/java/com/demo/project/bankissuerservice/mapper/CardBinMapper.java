package com.demo.project.bankissuerservice.mapper;

import com.demo.project.bankissuerservice.dto.response.BankInfoDtoResponse;
import com.demo.project.bankissuerservice.model.CardBinInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardBinMapper {

    BankInfoDtoResponse toCardBinInfoDtoResponse(CardBinInfo entity);
}
