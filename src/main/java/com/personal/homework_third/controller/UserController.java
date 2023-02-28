package com.personal.homework_third.controller;


import com.personal.homework_third.dto.LoginRequestDto;
import com.personal.homework_third.dto.LoginResponseDto;
import com.personal.homework_third.dto.SignupRequestDto;
import com.personal.homework_third.dto.SignupResponseDto;
import com.personal.homework_third.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userservice;

    @PostMapping("/api/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        return userservice.signup(signupRequestDto);
    }

    @ResponseBody
    @PostMapping("/api/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userservice.login(loginRequestDto, response);
    }
}
