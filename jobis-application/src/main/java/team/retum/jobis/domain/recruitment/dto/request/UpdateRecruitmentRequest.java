package team.retum.jobis.domain.recruitment.dto.request;

import lombok.Builder;
import team.retum.jobis.domain.recruitment.model.ProgressType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
public record UpdateRecruitmentRequest(
     Integer requiredGrade,
     LocalTime startTime,
     LocalTime endTime,
     List<String> requiredLicenses,
     List<ProgressType> hiringProgress,
     Integer trainPay,
     String pay,
     String benefits,
     boolean military,
     String submitDocument,
     LocalDate startDate,
     LocalDate endDate,
     String etc
) {}
