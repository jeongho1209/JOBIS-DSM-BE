package team.retum.jobis.domain.company.exception;

import team.retum.jobis.domain.company.exception.error.CompanyErrorCode;
import team.retum.jobis.global.error.exception.JobisException;

public class CompanyAlreadyExistsException extends JobisException {

    public static final JobisException EXCEPTION =
            new CompanyAlreadyExistsException();

    private CompanyAlreadyExistsException() {
        super(CompanyErrorCode.COMPANY_ALREADY_EXISTS);
    }
}