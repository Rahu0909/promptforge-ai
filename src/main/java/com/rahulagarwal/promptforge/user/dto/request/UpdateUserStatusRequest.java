package com.rahulagarwal.promptforge.user.dto.request;

import com.rahulagarwal.promptforge.user.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateUserStatusRequest(

        @NotNull
        UserStatus status

) {
}