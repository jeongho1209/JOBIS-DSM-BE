package team.retum.jobis.domain.user.spi;

import team.retum.jobis.domain.user.model.User;

public interface CommandUserPort {
    User saveUser(User user);
}
