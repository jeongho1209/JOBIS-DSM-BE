package team.returm.jobis.domain.teacher.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import team.returm.jobis.global.error.ErrorProperty;

@Getter
@AllArgsConstructor
public enum TeacherErrorCode implements ErrorProperty {

    TEACHER_NOT_FOUND(HttpStatus.NOT_FOUND, "Teacher Not Found");

    private final HttpStatus status;
    private final String message;
}
