package com.demo.project.bankissuerservice.util;

import static org.apache.logging.log4j.util.Strings.EMPTY;

import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CardUtil {

    private static final String ASTERISK = "*";

    public static String cleanCardNumber(String cardNumber) {
        if (Objects.isNull(cardNumber)) {
            throw new IllegalArgumentException("Card number cannot be null");
        }
        return cardNumber.replace(ASTERISK, EMPTY);
    }
}
