package team.retum.jobis.domain.recruitment.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.retum.jobis.domain.recruitment.dto.request.UpdateRecruitmentRequest;
import team.retum.jobis.domain.recruitment.model.ProgressType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateRecruitmentWebRequest {

    @Size(max = 500)
    private String preferentialTreatment;

    private Integer requiredGrade;

    @NotNull
    private int workHours;

    private List<String> requiredLicenses;

    @NotNull
    private List<ProgressType> hiringProgress;

    @NotNull
    private int trainPay;

    private Integer pay;

    @Size(max = 300)
    private String benefits;

    @NotNull
    private boolean military;

    @Size(max = 100)
    @NotNull
    private String submitDocument;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Size(max = 350)
    private String etc;

    public UpdateRecruitmentRequest toDomainRequest() {
        return UpdateRecruitmentRequest.builder()
                .preferentialTreatment(this.preferentialTreatment)
                .requiredGrade(this.requiredGrade)
                .workHours(this.workHours)
                .requiredLicenses(this.requiredLicenses)
                .hiringProgress(this.hiringProgress)
                .trainPay(this.trainPay)
                .pay(this.pay)
                .benefits(this.benefits)
                .military(this.military)
                .submitDocument(this.submitDocument)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .etc(this.etc)
                .build();
    }
}