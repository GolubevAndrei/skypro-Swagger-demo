package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student addStudent(Student student);

    Student findStudent(long id);

    Student editStudent(long id, Student student);

    Student deleteStudent(long id);

    Collection<Student> findByAge(int age);
}
