package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exeption.StudentNotFoundExeption;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        student.setId(null);
        return studentRepository.save(student);

    }
    @Override
    public Student findStudent(long id) {
//        return students.get(id);
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundExeption(id));
    }
    @Override
    public Student editStudent(long id, Student newStudent) {
        Student oldStudent = findStudent(id);
        oldStudent.setAge(newStudent.getAge());
        oldStudent.setFaculty(newStudent.getFaculty());
        oldStudent.setName(newStudent.getName());
        return studentRepository.save(oldStudent);
    }
    @Override
    public Student deleteStudent(long id) {
        Student oldStudent = findStudent(id);
        studentRepository.delete(oldStudent);
        return oldStudent;
    }
    @Override
    public Collection<Student> findByAge(int age) {
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {

        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

}

