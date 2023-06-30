package team.retum.jobis.domain.student.exception;

import team.retum.jobis.domain.student.exception.error.StudentErrorCode;
import team.retum.jobis.global.error.exception.JobisException;

public class ClassRoomNotFoundException extends JobisException {

    public static final JobisException EXCEPTION = new ClassRoomNotFoundException();

    private ClassRoomNotFoundException() {
        super(StudentErrorCode.CLASSROOM_NOT_FOUND);
    }
}