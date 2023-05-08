package team.returm.jobis.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import team.returm.jobis.domain.bookmark.domain.Bookmark;
import team.returm.jobis.domain.bookmark.domain.repository.BookmarkRepository;
import team.returm.jobis.domain.bookmark.exception.BookmarkNotFoundException;
import team.returm.jobis.domain.recruitment.domain.Recruitment;
import team.returm.jobis.domain.recruitment.domain.repository.RecruitmentRepository;
import team.returm.jobis.domain.recruitment.exception.RecruitmentNotFoundException;
import team.returm.jobis.domain.recruitment.facade.RecruitFacade;
import team.returm.jobis.domain.student.domain.Student;
import team.returm.jobis.domain.user.facade.UserFacade;
import team.returm.jobis.global.annotation.Service;

@RequiredArgsConstructor
@Service
public class DeleteBookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final RecruitFacade recruitFacade;
    private final UserFacade userFacade;

    public void execute(Long recruitmentId) {
        Student student = userFacade.getCurrentStudent();
        Recruitment recruitment = recruitFacade.queryRecruitmentById(recruitmentId);
        Bookmark bookmark = bookmarkRepository.queryBookmarkByRecruitmentAndStudent(recruitment, student)
                .orElseThrow(() -> BookmarkNotFoundException.EXCEPTION);

        bookmarkRepository.deleteBookmark(bookmark);
    }
}
