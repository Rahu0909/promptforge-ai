package com.rahulagarwal.promptforge.user.dto.request;

import com.rahulagarwal.promptforge.user.enums.UserStatus;

public record UserSearchRequest(

        String keyword,

        UserStatus status

) {
}