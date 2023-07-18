package team.retum.jobis.domain.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.retum.jobis.domain.user.persistence.User;
import team.retum.jobis.domain.user.exception.InvalidPasswordException;
import team.retum.jobis.domain.user.facade.UserFacade;
import com.example.jobisapplication.common.annotation.Service;

@RequiredArgsConstructor
@Service
public class CheckStudentPasswordService {

    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;

    public void execute(String password) {
        User user = userFacade.getCurrentUser();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw InvalidPasswordException.EXCEPTION;
        }
    }
}
