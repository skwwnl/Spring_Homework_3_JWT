package com.personal.homework_third.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SignupRequestDto {

    @NotNull(message = "id 입력은 필수입니다.")
//    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    // @Pattern(regexp = "^[a-z0-9]")
    private String username;

    @NotNull(message = "password 입력은 필수입니다.")
//    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9\\\\d`~!@#$%^&*()-_=+]{8,15}$")
    //@Pattern(regexp = "^[a-zA-Z0-9]")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
