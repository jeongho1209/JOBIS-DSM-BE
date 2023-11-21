package team.retum.jobis.domain.recruitment.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.retum.jobis.domain.recruitment.dto.request.ApplyRecruitmentRequest;
import team.retum.jobis.domain.recruitment.model.ProgressType;
import team.retum.jobis.global.annotation.ValidListElements;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ApplyRecruitmentWebRequest {

    @Valid
    @ValidListElements
    private List<RecruitAreaWebRequest> areas;

    private Integer requiredGrade;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private List<@NotNull String> requiredLicenses;

    @ValidListElements
    private List<ProgressType> hiringProgress;

    @NotNull
    private int trainPay;

    private String pay;

    @Size(max = 550)
    private String benefits;

    @NotNull
    private boolean militarySupport;

    @NotNull
    private boolean personalContact;

    @Size(max = 100)
    @NotNull
    private String submitDocument;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private boolean winterIntern;

    @Size(max = 350)
    private String etc;

    public ApplyRecruitmentRequest toDomainRequest() {
        return ApplyRecruitmentRequest.builder()
                .areas(
                        this.areas.stream()
                                .map(RecruitAreaWebRequest::toDomainRequest).toList()
                )
                .requiredGrade(this.requiredGrade)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .requiredLicenses(this.requiredLicenses)
                .hiringProgress(this.hiringProgress)
                .trainPay(this.trainPay)
                .pay(this.pay)
                .benefits(this.benefits)
                .militarySupport(this.militarySupport)
                .personalContact(this.personalContact)
                .submitDocument(this.submitDocument)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .winterIntern(this.winterIntern)
                .etc(this.etc)
                .build();
    }
}
