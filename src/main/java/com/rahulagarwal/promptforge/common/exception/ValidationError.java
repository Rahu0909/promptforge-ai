package com.rahulagarwal.promptforge.common.exception;

public record ValidationError(

        String field,

        String message

) {
}