package team.retum.jobis.domain.teacher.exception;

import team.retum.jobis.domain.teacher.exception.error.TeacherErrorCode;
import team.retum.jobis.global.error.exception.JobisException;

public class TeacherNotFoundException extends JobisException {
    public static final JobisException EXCEPTION = new TeacherNotFoundException();

    private TeacherNotFoundException() {
        super(TeacherErrorCode.TEACHER_NOT_FOUND);
    }
}