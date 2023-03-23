package team.returm.jobis.global.security.auth.teacher;

import team.returm.jobis.domain.teacher.domain.Teacher;
import team.returm.jobis.domain.teacher.domain.repository.TeacherRepository;
import team.returm.jobis.domain.teacher.exception.TeacherNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TeacherDetailsService implements UserDetailsService {
    private final TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String teacherId) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.queryTeacherById(
                UUID.fromString(teacherId)
        ).orElseThrow(() -> TeacherNotFoundException.EXCEPTION);

        return new TeacherDetails(teacher.getId());
    }
}