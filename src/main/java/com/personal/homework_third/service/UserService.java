package com.personal.homework_third.service;


import com.personal.homework_third.dto.LoginRequestDto;
import com.personal.homework_third.dto.LoginResponseDto;
import com.personal.homework_third.dto.SignupRequestDto;
import com.personal.homework_third.dto.SignupResponseDto;
import com.personal.homework_third.entity.User;
import com.personal.homework_third.jwt.JwtUtil;
import com.personal.homework_third.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);

        if(found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        User user = new User(signupRequestDto);
        userRepository.save(user);
        return new SignupResponseDto(HttpStatus.OK, "성공했습니다");
    }

    @Transactional (readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        // DB에 저장된 user의 password를 가져와 입력한 password를 비교한다.
        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));

        return new LoginResponseDto(HttpStatus.OK, "로그인에 성공하였습니다");
    }
}
