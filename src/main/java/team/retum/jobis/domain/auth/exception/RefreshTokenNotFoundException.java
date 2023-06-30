package team.retum.jobis.domain.auth.exception;

import team.retum.jobis.domain.auth.exception.error.AuthErrorCode;
import team.retum.jobis.global.error.exception.JobisException;

public class RefreshTokenNotFoundException extends JobisException {
    public static final JobisException EXCEPTION = new RefreshTokenNotFoundException();

    private RefreshTokenNotFoundException() {
        super(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}