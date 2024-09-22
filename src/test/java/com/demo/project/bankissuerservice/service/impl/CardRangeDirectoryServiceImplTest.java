package com.demo.project.bankissuerservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.demo.project.bankissuerservice.exception.CardBinInfoUpdateException;
import com.demo.project.bankissuerservice.repository.CardBinInfoRepository;
import java.io.IOException;
import java.util.zip.ZipInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

class CardRangeDirectoryServiceImplTest {

    @Mock
    private CardBinInfoRepository repository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CardRangeDirectoryServiceImpl cardRangeDirectoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenNoZipEntryFound() throws IOException {
        ZipInputStream zipInputStream = mock(ZipInputStream.class);
        when(zipInputStream.getNextEntry()).thenReturn(null);

        assertThrows(CardBinInfoUpdateException.class, () -> {
            cardRangeDirectoryService.updateCardRangeDirectory();
        });
    }
}
