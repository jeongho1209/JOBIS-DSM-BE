package com.example.jobis.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    BAD_EMAIL(400, "Bad Email Domain"),
    INVALID_EXTENSION(400, "Invalid Extension File"),

    UNVERIFIED_EMAIL(401, "Unverified Email"),
    BAD_AUTH_CODE(401, "Bad Auth Code"),
    EXPIRED_TOKEN(401,"Token Expired" ),
    INVALID_TOKEN(401,"Invalid Token"),
    INVALID_PASSWORD(401, "invalid password"),
    INVALID_CODE(401, "invalid code"),
    INVALID_GRADE(401, "Invalid Grade"),


    USER_NOT_FOUND(404, "User Not Found"),
    COMPANY_NOT_FOUND(404, "Company Not Found"),
    STUDENT_NOT_FOUND(404, "Student Not Found"),
    MAIL_SEND_FAIL(404, "Mail Send Fail"),
    FILE_NOT_FOUND(404, "File not Found"),
<<<<<<< HEAD
    RECRUIT_NOT_FOUND(404, "Recruit Not Found"),

=======
    CODE_NOT_FOUND(404, "Code Not Found"),
    RECRUIT_NOT_FOUND(404, "Recruit Not Found"),
    RECRUIT_AREA_NOT_FOUND(404, "Recruit Area Not Found"),
    RECRUIT_AREA_CODE_NOT_FOUND(404, "Recruit Area Code Not Found"),
>>>>>>> main
    REFRESH_TOKEN_NOT_FOUND(404, "Refresh Token Not Found"),
    APPLICATION_NOT_FOUND(404, "Application Not Found"),

    COMPANY_ALREADY_EXISTS(409, "Company Already Exists"),
    STUDENT_ALREADY_EXISTS(409, "Student Already Exists"),
    APPLICATION_ALREADY_EXISTS(409, "Application Already Exists")
    ;

    private final Integer status;
    private final String message;
}
