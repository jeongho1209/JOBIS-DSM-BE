package team.retum.jobis.domain.acceptance.service;

import lombok.RequiredArgsConstructor;
import team.retum.jobis.domain.acceptance.persistence.entity.AcceptanceEntity;
import team.retum.jobis.domain.acceptance.persistence.repository.AcceptanceRepository;
import team.retum.jobis.domain.acceptance.persistence.repository.vo.ApplicationDetailVO;
import team.retum.jobis.domain.acceptance.presentation.dto.request.RegisterEmploymentContractWebRequest;
import com.example.jobisapplication.domain.application.model.ApplicationStatus;
import team.retum.jobis.domain.application.persistence.repository.ApplicationRepository;
import com.example.jobisapplication.domain.application.exception.ApplicationNotFoundException;
import com.example.jobisapplication.domain.application.exception.ApplicationStatusCannotChangeException;
import team.retum.jobis.domain.student.persistence.entity.StudentEntity;
import com.example.jobisapplication.common.annotation.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterEmploymentContractService {

    private final AcceptanceRepository acceptanceRepository;
    private final ApplicationRepository applicationRepository;

    public void execute(RegisterEmploymentContractWebRequest request) {
        List<ApplicationDetailVO> applications = applicationRepository.queryApplicationDetailsByIds(request.getApplicationIds());
        if (applications.size() != request.getApplicationIds().size()) {
            throw ApplicationNotFoundException.EXCEPTION;
        }

        List<AcceptanceEntity> acceptanceEntities = applications.stream()
                .map(
                        application -> {
                            if (application.getStatus() != ApplicationStatus.FIELD_TRAIN) {
                                throw ApplicationStatusCannotChangeException.EXCEPTION;
                            }

                            return AcceptanceEntity.builder()
                                    .studentName(application.getStudentName())
                                    .company(application.getCompanyEntity())
                                    .studentGcn(StudentEntity.processGcn(
                                            application.getStudentGrade(),
                                            application.getStudentClassNumber(),
                                            application.getStudentNumber()
                                    ))
                                    .contractDate(LocalDate.now())
                                    .year(Year.now().getValue())
                                    .tech(request.getCodeKeywords())
                                    .businessArea(application.getCompanyEntity().getBusinessArea())
                                    .build();
                        }
                ).toList();

        acceptanceRepository.saveAllAcceptance(acceptanceEntities);
        applicationRepository.deleteApplicationByIds(
                applications.stream().map(ApplicationDetailVO::getId).toList()
        );
    }
}
