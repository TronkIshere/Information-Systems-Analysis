package com.tronk.analysis.dto.request.authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignInRequest {
    @NotBlank
    String loginName;
    @NotBlank
    String password;
}
