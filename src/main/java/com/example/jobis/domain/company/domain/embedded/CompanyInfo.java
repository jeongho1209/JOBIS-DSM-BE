package com.example.jobis.domain.company.domain.embedded;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CompanyInfo {

    @Column(length = 40, nullable = false)
    private String representativeName;

    @Column(length = 8, nullable = false)
    private String foundedAt;

    @Column(nullable = false)
    private Long workerNumber;

    @Column(nullable = false)
    private Long take;
}
