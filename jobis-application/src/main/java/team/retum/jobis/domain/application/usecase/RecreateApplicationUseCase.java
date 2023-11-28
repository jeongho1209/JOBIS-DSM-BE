package team.retum.jobis.domain.application.usecase;

import lombok.RequiredArgsConstructor;
import team.retum.jobis.common.annotation.UseCase;
import team.retum.jobis.domain.application.dto.request.CreateApplicationRequest;
import team.retum.jobis.domain.application.exception.ApplicationNotFoundException;
import team.retum.jobis.domain.application.exception.ApplicationStatusCannotChangeException;
import team.retum.jobis.domain.application.model.Application;
import team.retum.jobis.domain.application.model.ApplicationAttachment;
import team.retum.jobis.domain.application.model.ApplicationStatus;
import team.retum.jobis.domain.application.spi.CommandApplicationPort;
import team.retum.jobis.domain.application.spi.QueryApplicationPort;

@RequiredArgsConstructor
@UseCase
public class RecreateApplicationUseCase {

    private final QueryApplicationPort queryApplicationPort;
    private final CommandApplicationPort commandApplicationPort;

    public void execute(Long applicationId, CreateApplicationRequest request) {
        Application application = queryApplicationPort.queryApplicationById(applicationId)
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        if (application.getApplicationStatus() != ApplicationStatus.REJECTED) {
            throw ApplicationStatusCannotChangeException.EXCEPTION;
        }

        commandApplicationPort.saveApplication(
                application.reapply(
                        request.getAttachments().stream()
                                .map(attachment -> new ApplicationAttachment(attachment.getUrl(), attachment.getType()))
                                .toList()
                )
        );
    }
}
