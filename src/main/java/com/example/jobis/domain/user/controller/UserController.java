package com.example.jobis.domain.user.controller;

import com.example.jobis.domain.user.controller.dto.request.LoginRequest;
import com.example.jobis.domain.user.controller.dto.response.TokenResponse;
import com.example.jobis.domain.user.controller.dto.response.UserAuthResponse;
import com.example.jobis.domain.user.service.LoginService;
import com.example.jobis.domain.user.service.TokenReissueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final LoginService loginService;
    private final TokenReissueService tokenReissueService;

    @PostMapping("/login")
    public UserAuthResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.execute(request);
    }

    @PutMapping("/reissue")
    public TokenResponse reissue(@RequestHeader("X-Refresh-Token") String token) {
        return tokenReissueService.execute(token);
    }
}
