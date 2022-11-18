package com.example.jobis.domain.recruit.domain;

import com.example.jobis.domain.company.domain.Company;
import com.example.jobis.domain.recruit.domain.enums.RecruitStatus;
import com.example.jobis.domain.recruit.domain.type.Pay;
import com.example.jobis.domain.recruit.domain.type.RecruitDate;
import com.example.jobis.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Recruit extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_id")
    private Long id;

    @Column(nullable = false)
    private Year recruitYear;

    @Enumerated(EnumType.STRING)
    private RecruitStatus status;

    @Column(length = 100)
    private String benefit;

    @Column(columnDefinition = "BIT(1)", nullable = false)
    private boolean military;

    @Column(length = 100)
    private String etc;

    @Embedded
    private RecruitDate recruitDate;

    @Embedded
    private Pay pay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "recruit")
    private List<RecruitArea> recruitAreaList = new ArrayList<>();

    @OneToMany(mappedBy = "recruit")
    private List<RequiredLicences> requiredLicencesList = new ArrayList<>();

    @Builder
    public Recruit(Year year, RecruitStatus status, Integer trainPay, Integer pay,
                   LocalDate startDate, LocalDate endDate, Company company, String benefit, boolean military, String etc
    ) {
        this.recruitYear = year;
        this.status = status;
        this.benefit = benefit;
        this.recruitDate = new RecruitDate(startDate, endDate);
        this.pay = new Pay(trainPay, pay);
        this.company = company;
        this.military = military;
        this.etc = etc;
    }
}
