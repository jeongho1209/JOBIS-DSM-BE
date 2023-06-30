package team.retum.jobis.domain.student.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.retum.jobis.domain.student.domain.Student;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class StudentRepository {

    private final StudentJpaRepository studentJpaRepository;

    public Optional<Student> queryStudentById(Long studentId) {
        return studentJpaRepository.findById(studentId);
    }

}