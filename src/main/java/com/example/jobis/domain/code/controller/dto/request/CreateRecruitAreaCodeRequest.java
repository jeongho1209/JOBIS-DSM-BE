package com.example.jobis.domain.code.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateRecruitAreaCodeRequest {

    private List<Long> codeList;
}
