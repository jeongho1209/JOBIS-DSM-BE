package team.returm.jobis.domain.company.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import team.returm.jobis.global.error.ErrorProperty;

@Getter
@AllArgsConstructor
public enum CompanyErrorCode implements ErrorProperty {

    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "Company Not Found"),

    COMPANY_ALREADY_EXISTS(HttpStatus.CONFLICT, "Company Already Exists");

    private final HttpStatus status;
    private final String message;
}
