package team.retum.jobis.domain.recruitment.dto.request;

import lombok.Builder;
import lombok.Getter;
import team.retum.jobis.domain.recruitment.model.ProgressType;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class UpdateRecruitmentRequest {

    private String preferentialTreatment;

    private Integer requiredGrade;

    private Integer workHours;

    private List<String> requiredLicenses;

    private List<ProgressType> hiringProgress;

    private Integer trainPay;

    private Integer pay;

    private String benefits;

    private boolean military;

    private String submitDocument;

    private LocalDate startDate;

    private LocalDate endDate;

    private String etc;
}
