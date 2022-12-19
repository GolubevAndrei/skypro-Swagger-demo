package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private final Map<Long, Student> students = new HashMap<>();
    private long idNumber = 0;

    @Override
    public Student addStudent(Student student) {
        student.setId(idNumber++);
        students.put(student.getId(), student);
        return student;
    }
    @Override
    public Student findStudent(long id) {
        return students.get(id);
    }
    @Override
    public Student editStudent(long id, Student student) {
        if (!students.containsKey(id)) {
            return null;
        }
        students.put(id, student);
        return student;
    }
    @Override
    public Student deleteStudent(long id) {
        return students.remove(id);
    }

}
