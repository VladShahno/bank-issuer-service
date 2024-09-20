package com.demo.project.bankissuerservice.util.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    @UtilityClass
    public static class Validation {

        public static final String CARD_REGEX = "^[0-9*]+$";
        public static final String CARD_CAN_NOT_BE_EMPTY = "Card can't be empty";

    }
}
