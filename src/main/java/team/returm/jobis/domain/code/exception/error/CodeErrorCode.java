package team.returm.jobis.domain.code.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import team.returm.jobis.global.error.ErrorProperty;

@Getter
@AllArgsConstructor
public enum CodeErrorCode implements ErrorProperty {

    RECRUIT_AREA_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "Recruit Area Code Not Found"),
    CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "Code Not Found");

    private final HttpStatus status;
    private final String message;
}