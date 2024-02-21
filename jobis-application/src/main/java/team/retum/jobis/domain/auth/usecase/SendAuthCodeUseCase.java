package team.retum.jobis.domain.auth.usecase;

import lombok.RequiredArgsConstructor;
import team.retum.jobis.common.annotation.UseCase;
import team.retum.jobis.common.spi.SendEmailPort;
import team.retum.jobis.domain.auth.model.AuthCode;
import team.retum.jobis.domain.auth.model.AuthCodeType;
import team.retum.jobis.domain.auth.spi.CommandAuthCodePort;
import team.retum.jobis.domain.student.exception.StudentAlreadyExistsException;
import team.retum.jobis.domain.student.exception.StudentNotFoundException;
import team.retum.jobis.domain.user.spi.QueryUserPort;

import java.util.Random;

@RequiredArgsConstructor
@UseCase
public class SendAuthCodeUseCase {

    private final CommandAuthCodePort commandAuthCodePort;
    private final QueryUserPort queryUserPort;
    private final SendEmailPort sendEmailPort;

    public void execute(String email, AuthCodeType authCodeType) {
        if (authCodeType == AuthCodeType.SIGN_UP) {
            if (queryUserPort.existsUserByAccountId(email)) {
                throw StudentAlreadyExistsException.EXCEPTION;
            }
        } else {
            if (!queryUserPort.existsUserByAccountId(email)) {
                throw StudentNotFoundException.EXCEPTION;
            }
        }

        AuthCode authCode = AuthCode.builder()
                .code(String.valueOf(new Random().nextInt(899999) + 100000))
                .ttl(300)
                .isVerified(false)
                .email(email)
                .build();
        commandAuthCodePort.saveAuthCode(authCode);

        sendEmailPort.sendMail(authCode.getCode(), authCode.getEmail());
    }
}
