package team.retum.jobis.domain.bookmark.exception;

import team.retum.jobis.domain.bookmark.exception.error.BookmarkErrorCode;
import team.retum.jobis.global.error.exception.JobisException;

public class BookmarkNotFoundException extends JobisException {

    public static final JobisException EXCEPTION = new BookmarkNotFoundException();

    private BookmarkNotFoundException() {
        super(BookmarkErrorCode.BOOKMARK_NOT_FOUND);
    }
}