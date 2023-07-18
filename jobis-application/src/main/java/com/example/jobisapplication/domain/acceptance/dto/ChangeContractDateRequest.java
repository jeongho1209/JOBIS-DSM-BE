package com.example.jobisapplication.domain.acceptance.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ChangeContractDateRequest {

    private List<Long> acceptanceIds;

    private LocalDate contractDate;
}
