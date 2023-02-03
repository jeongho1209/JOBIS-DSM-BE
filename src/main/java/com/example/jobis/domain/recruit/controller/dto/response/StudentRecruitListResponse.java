package com.example.jobis.domain.recruit.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.util.Set;

@Getter
@Builder
public class StudentRecruitListResponse {

    private final Long recruitId;
    private final String companyName;
    private final String companyProfileUrl;
    private final Integer trainPay;
    private final boolean military;
    private final Integer totalHiring;
    private final Set<String> jobCodeList;
}
