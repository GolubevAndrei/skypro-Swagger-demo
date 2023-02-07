package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findAllByAge(int age);

    Collection<Student> findByAgeBetween(int minAge, int maxAge);

    Collection<Student> findAllByFaculty_Id(long facultyId);

    @Query(value = "select count(*) from Student", nativeQuery = true)
    Long getAmountOfAllStudents();

    @Query(value = "select avg(age) from Student", nativeQuery = true)
    Double getAverageAgeOfAllStudents();

    @Query(value = "select * from Student order by id desc limit 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
