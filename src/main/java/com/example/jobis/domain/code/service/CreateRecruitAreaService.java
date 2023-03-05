package com.example.jobis.domain.code.service;

import com.example.jobis.domain.code.controller.dto.request.CreateRecruitAreaRequest;
import com.example.jobis.domain.code.domain.Code;
import com.example.jobis.domain.code.domain.RecruitAreaCode;
import com.example.jobis.domain.code.domain.repository.RecruitAreaCodeRepository;
import com.example.jobis.domain.code.facade.CodeFacade;
import com.example.jobis.domain.recruitment.domain.RecruitArea;
import com.example.jobis.domain.recruitment.domain.Recruitment;
import com.example.jobis.domain.recruitment.domain.repository.RecruitAreaRepository;
import com.example.jobis.domain.recruitment.facade.RecruitFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CreateRecruitAreaService {


    private final RecruitAreaRepository recruitAreaRepository;
    private final RecruitAreaCodeRepository recruitAreaCodeRepository;
    private final CodeFacade codeFacade;
    private final RecruitFacade recruitFacade;

    @Transactional
    public void execute(CreateRecruitAreaRequest request, UUID recruitmentId) {

        Recruitment recruitment = recruitFacade.getRecruitById(recruitmentId);

        RecruitArea recruitArea = recruitAreaRepository.save(RecruitArea.builder()
                .majorTask(request.getMajorTask())
                .hiredCount(request.getHiring())
                .recruitment(recruitment)
                .build()
        );
        List<Long> codes = new ArrayList<>();
        codes.addAll(request.getTech());
        codes.addAll(request.getJob());
        List<Code> codeList = codeFacade.findAllCodeById(codes);
        List<RecruitAreaCode> recruitAreaCodeList = codeList.stream()
                .map(code -> new RecruitAreaCode(recruitArea, code))
                .toList();
        recruitAreaCodeRepository.saveAll(recruitAreaCodeList);
    }
}
