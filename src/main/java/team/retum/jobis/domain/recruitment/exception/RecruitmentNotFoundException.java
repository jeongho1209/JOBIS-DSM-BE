package team.retum.jobis.domain.recruitment.exception;

import team.retum.jobis.domain.recruitment.exception.error.RecruitmentErrorCode;
import team.retum.jobis.global.error.exception.JobisException;

public class RecruitmentNotFoundException extends JobisException {
    public static final JobisException EXCEPTION = new RecruitmentNotFoundException();

    private RecruitmentNotFoundException() {
        super(RecruitmentErrorCode.RECRUITMENT_NOT_FOUND);
    }
}