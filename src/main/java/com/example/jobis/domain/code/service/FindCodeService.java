package com.example.jobis.domain.code.service;

import com.example.jobis.domain.code.domain.Code;
import com.example.jobis.domain.code.domain.enums.CodeType;
import com.example.jobis.domain.code.domain.repository.CodeRepository;
import com.example.jobis.domain.code.presentaion.dto.response.CodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindCodeService {
    private final CodeRepository codeRepository;

    public List<CodeResponse> execute(String keyword, CodeType type) {
        return codeRepository.findByKeywordStartsWithAndCodeType(keyword, type)
                .stream()
                .map(Code::to)
                .collect(Collectors.toList());
    }
}