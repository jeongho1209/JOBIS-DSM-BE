package team.retum.jobis.domain.notice.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.retum.jobis.domain.notice.model.Notice;
import team.retum.jobis.domain.notice.persistence.mapper.NoticeMapper;
import team.retum.jobis.domain.notice.persistence.repository.NoticeJpaRepository;
import team.retum.jobis.domain.notice.spi.NoticePort;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class NoticePersistenceAdapter implements NoticePort {

    private final NoticeJpaRepository noticeJpaRepository;
    private final NoticeMapper noticeMapper;

    @Override
    public void saveNotice(Notice notice) {
        noticeMapper.toDomain(
                noticeJpaRepository.save(
                        noticeMapper.toEntity(notice)
                )
        );
    }

    @Override
    public Optional<Notice> queryNoticeById(Long noticeId) {
        return noticeJpaRepository.findById(noticeId)
                .map(noticeMapper::toDomain);
    }

    @Override
    public void deleteNotice(Notice notice) {
        noticeJpaRepository.delete(noticeMapper.toEntity(notice));
    }
}