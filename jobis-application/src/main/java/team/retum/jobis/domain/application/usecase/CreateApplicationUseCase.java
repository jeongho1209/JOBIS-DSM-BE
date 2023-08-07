package team.retum.jobis.domain.application.usecase;

import lombok.RequiredArgsConstructor;
import team.retum.jobis.common.annotation.UseCase;
import team.retum.jobis.common.spi.SecurityPort;
import team.retum.jobis.domain.application.dto.request.CreateApplicationRequest;
import team.retum.jobis.domain.application.exception.ApplicationAlreadyExistsException;
import team.retum.jobis.domain.application.model.Application;
import team.retum.jobis.domain.application.model.ApplicationAttachment;
import team.retum.jobis.domain.application.model.ApplicationStatus;
import team.retum.jobis.domain.application.spi.CommandApplicationPort;
import team.retum.jobis.domain.application.spi.QueryApplicationPort;
import team.retum.jobis.domain.recruitment.exception.RecruitmentNotFoundException;
import team.retum.jobis.domain.recruitment.model.Recruitment;
import team.retum.jobis.domain.recruitment.spi.QueryRecruitmentPort;
import team.retum.jobis.domain.student.exception.StudentNotFoundException;
import team.retum.jobis.domain.student.model.Student;
import team.retum.jobis.domain.student.spi.QueryStudentPort;

import java.util.List;

@RequiredArgsConstructor
@UseCase
public class CreateApplicationUseCase {

    private final CommandApplicationPort commandApplicationPort;
    private final QueryApplicationPort queryApplicationPort;
    private final QueryRecruitmentPort queryRecruitmentPort;
    private final SecurityPort securityPort;
    private final QueryStudentPort queryStudentPort;

    public void execute(CreateApplicationRequest request, Long recruitmentId) {
        Long currentUserId = securityPort.getCurrentUserId();
        Student student = queryStudentPort.queryStudentById(currentUserId)
                .orElseThrow(() -> StudentNotFoundException.EXCEPTION);
        student.checkIs3rdGrade();

        Recruitment recruitment = queryRecruitmentPort.queryRecruitmentById(recruitmentId)
                .orElseThrow(() -> RecruitmentNotFoundException.EXCEPTION);
        recruitment.checkIsApplicable();

        if (queryApplicationPort.existsApplicationByStudentIdAndApplicationStatusIn(
                student.getId(),
                List.of(
                        ApplicationStatus.APPROVED,
                        ApplicationStatus.FIELD_TRAIN,
                        ApplicationStatus.PASS
                )
        )) {
            throw ApplicationAlreadyExistsException.EXCEPTION;
        }

        Application application = commandApplicationPort.saveApplication(
                Application.builder()
                        .studentId(student.getId())
                        .recruitmentId(recruitment.getId())
                        .applicationStatus(ApplicationStatus.REQUESTED)
                        .build()
        );

        List<ApplicationAttachment> applicationAttachments = request.getAttachments()
                .stream()
                .map(attachment ->
                        ApplicationAttachment.builder()
                                .attachmentUrl(attachment.getUrl())
                                .type(attachment.getType())
                                .applicationId(application.getId())
                                .build()
                ).toList();

        commandApplicationPort.saveAllApplicationAttachment(applicationAttachments);
    }
}