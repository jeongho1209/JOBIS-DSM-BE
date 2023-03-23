package team.returm.jobis.domain.company.exception;

import team.returm.jobis.global.error.exception.ErrorCode;
import team.returm.jobis.global.error.exception.JobisException;

public class CompanyNotFoundException extends JobisException {
    public static final JobisException EXCEPTION = new CompanyNotFoundException();

    private CompanyNotFoundException() {
        super(ErrorCode.COMPANY_NOT_FOUND);
    }
}