package com.example.jobis.domain.company.controller;

import com.example.jobis.domain.company.controller.dto.request.ExistsCompanyRequest;
import com.example.jobis.domain.company.controller.dto.response.ExistsCompanyResponse;
import com.example.jobis.domain.company.service.ExistsCompanyService;
import com.example.jobis.domain.user.controller.dto.response.TokenResponse;
import com.example.jobis.domain.company.controller.dto.request.CompanyDetailsRequest;
import com.example.jobis.domain.company.controller.dto.request.RegisterCompanyRequest;
import com.example.jobis.domain.company.service.CreateCompanyDetailsService;
import com.example.jobis.domain.company.service.RegisterCompanyService;
import com.example.jobis.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/companies")
@RestController
public class CompanyController {

    private final RegisterCompanyService registerCompanyService;
    private final CreateCompanyDetailsService createCompanyDetailsService;
    private final ExistsCompanyService existsCompanyService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TokenResponse register(@RequestBody @Valid RegisterCompanyRequest request) {
        return registerCompanyService.execute(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/details")
    public void companyDetails(@RequestBody @Valid CompanyDetailsRequest request) {
        createCompanyDetailsService.execute(request);
    }

    @GetMapping("/exists")
    public ExistsCompanyResponse companyExists(@RequestBody @Valid ExistsCompanyRequest request) {
        return existsCompanyService.execute(request);
    }
}
