package team.retum.jobis.domain.company.usecase;

import team.retum.jobis.common.annotation.UseCase;
import team.retum.jobis.common.spi.SecurityPort;
import team.retum.jobis.domain.company.dto.request.UpdateCompanyDetailsRequest;
import team.retum.jobis.domain.company.exception.CompanyNotFoundException;
import team.retum.jobis.domain.company.model.Company;
import team.retum.jobis.domain.company.spi.CommandCompanyPort;
import team.retum.jobis.domain.company.spi.QueryCompanyPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class UpdateCompanyDetailsUseCase {

    private final SecurityPort securityPort;
    private final QueryCompanyPort queryCompanyPort;
    private final CommandCompanyPort commandCompanyPort;

    public void execute(UpdateCompanyDetailsRequest request) {
        Long currentUserId = securityPort.getCurrentUserId();
        Company company = queryCompanyPort.queryCompanyById(currentUserId)
                .orElseThrow(() -> CompanyNotFoundException.EXCEPTION);

        commandCompanyPort.saveCompany(
                company.update(
                        request.getMainAddress(), request.getMainZipCode(),
                        request.getSubAddress(), request.getSubZipCode(),
                        request.getTake(), request.getWorkerNumber(),
                        request.getManagerName(), request.getManagerPhoneNo(),
                        request.getSubManagerName(), request.getSubManagerPhoneNo(),
                        request.getCompanyIntroduce(), request.getCompanyProfileUrl(),
                        request.getFax(), request.getEmail()
                )
        );
    }
}
