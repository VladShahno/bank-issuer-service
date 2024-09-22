package com.demo.project.bankissuerservice.controller;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.demo.project.bankissuerservice.dto.request.BankInfoDtoRequest;
import com.demo.project.bankissuerservice.dto.response.BankInfoDtoResponse;
import com.demo.project.bankissuerservice.service.IssuingBankSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(IssuingBankController.class)
class IssuingBankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssuingBankSearchService issuingBankSearchService;

    @Test
    void shouldReturnBankInfo() throws Exception {
        BankInfoDtoRequest request = new BankInfoDtoRequest();
        request.setCard("4441110000004100000");

        BankInfoDtoResponse response = new BankInfoDtoResponse();
        response.setBankName("Demo Bank");

        when(issuingBankSearchService.findIssuingBankInfo(anyString())).thenReturn(response);

        mockMvc.perform(post("/api/bin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"card\": \"4441110000004100000\" }"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Demo Bank"));
    }
}
