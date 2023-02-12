package com.example.jobis.domain.recruit.domain;

import com.example.jobis.domain.company.domain.Company;
import com.example.jobis.domain.recruit.domain.enums.RecruitStatus;
import com.example.jobis.domain.recruit.domain.type.Pay;
import com.example.jobis.domain.recruit.domain.type.RecruitDate;
import com.example.jobis.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
<<<<<<< HEAD:src/main/java/com/example/jobis/domain/recruit/domain/Recruit.java
=======
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
>>>>>>> main:src/main/java/com/example/jobis/domain/recruit/domain/Recruitment.java

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Recruitment extends BaseEntity {

    @NotNull
    @Column(columnDefinition = "YEAR")
    private int recruitYear;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private RecruitStatus status;

<<<<<<< HEAD:src/main/java/com/example/jobis/domain/recruit/domain/Recruit.java
    @Column(nullable = false)
    private int requiredGrade;

    @Column(nullable = false)
    private int workHours;

    @Column(length = 500, nullable = false)
    private String submitDocument;

    @Column(length = 1000)
    private String benefit;
=======
>>>>>>> main:src/main/java/com/example/jobis/domain/recruit/domain/Recruitment.java

    private String preferentialTreatment;

    private String requiredLicenses;

    @Column(columnDefinition = "TINYINT(100)")
    private Integer requiredGrade;

    @NotNull
    @Column(columnDefinition = "TINYINT(50)", nullable = false)
    private int workingHours;

    private String benefit;

    @NotNull
    @Column(columnDefinition = "BOOL")
    private boolean militarySupport;

    @NotNull
    @Column(columnDefinition = "VARCHAR(100)")
    private String hiringProgress;

    @Column(columnDefinition = "VARCHAR(100)")
    private String submitDocument;

    @Column(columnDefinition = "TEXT")
    private String etc;

    @Embedded
    private RecruitDate recruitDate;

    @Embedded
    private Pay pay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "recruitment", orphanRemoval = true)
    private List<RecruitArea> recruitAreaList = new ArrayList<>();

    @Builder
<<<<<<< HEAD:src/main/java/com/example/jobis/domain/recruit/domain/Recruit.java
    public Recruit(int recruitYear, RecruitStatus status, int requiredGrade, int workHours, String submitDocument, Integer trainPay, Integer pay,
                   LocalDate startDate, LocalDate endDate, Company company, String benefit, String requiredLicenses,
                   boolean military, String etc, String preferentialTreatment, String hiringProgress
    ) {
        this.requiredGrade = requiredGrade;
        this.workHours = workHours;
        this.submitDocument = submitDocument;
=======
    public Recruitment(int recruitYear, RecruitStatus status, Integer trainPay, Integer pay, int workingHours, String submitDocument,
                       LocalDate startDate, LocalDate endDate, Company company, String benefit, String requiredLicenses,
                       boolean militarySupport, String etc, String preferentialTreatment, String hiringProgress, Integer requiredGrade
    ) {
        this.workingHours = workingHours;
>>>>>>> main:src/main/java/com/example/jobis/domain/recruit/domain/Recruitment.java
        this.hiringProgress = hiringProgress;
        this.submitDocument = submitDocument;
        this.requiredGrade = requiredGrade;
        this.recruitYear = recruitYear;
        this.status = status;
        this.benefit = benefit;
        this.preferentialTreatment = preferentialTreatment;
        this.recruitDate = new RecruitDate(startDate, endDate);
        this.pay = new Pay(trainPay, pay);
        this.company = company;
        this.requiredLicenses = requiredLicenses;
        this.militarySupport = militarySupport;
        this.etc = etc;
    }

    public void update(Integer trainPay, Integer pay, int workingHours, String submitDocument,
                       LocalDate startDate, LocalDate endDate, String benefit, String requiredLicenses,
                       boolean militarySupport, String etc, String preferentialTreatment, String hiringProgress, Integer requiredGrade
    ) {
        this.workingHours = workingHours;
        this.hiringProgress = hiringProgress;
        this.submitDocument = submitDocument;
        this.requiredGrade = requiredGrade;
        this.benefit = benefit;
        this.preferentialTreatment = preferentialTreatment;
        this.recruitDate = new RecruitDate(startDate, endDate);
        this.pay = new Pay(trainPay, pay);
        this.requiredLicenses = requiredLicenses;
        this.militarySupport = militarySupport;
        this.etc = etc;
    }

    public Recruitment changeStatus(RecruitStatus status) {
        this.status = status;
        return this;
    }
}
