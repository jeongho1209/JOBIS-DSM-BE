package com.example.jobis.domain.code.service;

import com.example.jobis.domain.code.domain.Code;
import com.example.jobis.domain.code.domain.repository.RecruitAreaCodeRepository;
import com.example.jobis.domain.code.facade.CodeFacade;
import com.example.jobis.domain.recruitment.domain.RecruitArea;
import com.example.jobis.domain.recruitment.facade.RecruitAreaFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DeleteRecruitAreaCodeService {

    private final RecruitAreaCodeRepository recruitAreaCodeRepository;
    private final RecruitAreaFacade recruitAreaFacade;
    private final CodeFacade codeFacade;

    @Transactional
    public void execute(UUID recruitAreaId, Long codeId) {

        RecruitArea recruitArea = recruitAreaFacade.getRecruitAreaById(recruitAreaId);
        Code code = codeFacade.findCodeById(codeId);

        recruitAreaCodeRepository.deleteByRecruitAreaIdAndCodeId(recruitArea, code);
    }
}
