package com.example.jobis.domain.code.controller;

import com.example.jobis.domain.code.domain.enums.CodeType;
import com.example.jobis.domain.code.controller.dto.response.CodeResponse;
import com.example.jobis.domain.code.service.FindCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {
    private final FindCodeService findCodeService;

    @GetMapping("/tech")
    public List<CodeResponse> findTechCode(@RequestParam("keyword") String keyword) {
        return findCodeService.execute(keyword, CodeType.TECH);
    }

    @GetMapping("/licenses")
    public List<CodeResponse> findLicenseCode(@RequestParam("keyword") String keyword) {
        return findCodeService.execute(keyword, CodeType.LICENSE);
    }

    @GetMapping("/job")
    public List<CodeResponse> findJobCode() {
        return findCodeService.execute("", CodeType.JOB);
    }

}
