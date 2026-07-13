package com.rahulagarwal.promptforge.codegen.util;

import org.springframework.stereotype.Component;

@Component
public class TokenEstimator {

    public int estimate(String text) {

        if (text == null || text.isBlank()) {
            return 0;
        }

        return Math.max(1, text.length() / 4);
    }

}