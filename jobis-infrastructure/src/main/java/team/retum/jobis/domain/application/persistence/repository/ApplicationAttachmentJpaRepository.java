package team.retum.jobis.domain.application.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.retum.jobis.domain.application.persistence.entity.ApplicationAttachmentEntity;

public interface ApplicationAttachmentJpaRepository extends JpaRepository<ApplicationAttachmentEntity, String> {
}