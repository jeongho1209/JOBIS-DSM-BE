package team.returm.jobis.domain.user.domain.repository;

import team.returm.jobis.domain.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends CrudRepository<User, UUID> {
    Optional<User> findByAccountId(String accountId);
}