package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exeption.StudentNotFoundExeption;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;

@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    @Value("${avatars.dir.path}")
    private String avatarsDir;
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public StudentServiceImpl(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("Was invoked method for addStudent");
        student.setId(null);
        return studentRepository.save(student);

    }
    @Override
    public Student findStudent(long id) {
        logger.info("Was invoked method for findStudent");
//        return students.get(id);
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundExeption(id));
    }
    @Override
    public Student editStudent(long id, Student newStudent) {
        logger.info("Was invoked method for editStudent");
        Student oldStudent = findStudent(id);
        oldStudent.setAge(newStudent.getAge());
        oldStudent.setFaculty(newStudent.getFaculty());
        oldStudent.setName(newStudent.getName());
        return studentRepository.save(oldStudent);
    }
    @Override
    public Student deleteStudent(long id) {
        logger.info("Was invoked method for deleteStudent");
        Student oldStudent = findStudent(id);
        studentRepository.delete(oldStudent);
        return oldStudent;
    }
    @Override
    public Collection<Student> findByAge(int age) {

        logger.info("Was invoked method for findByAge");
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {

        logger.info("Was invoked method for findByAgeBetween");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }
    @Override
    public Avatar findAvatar(Long id) {
        logger.info("Was invoked method for findAvatar");
        return avatarRepository.findByStudentId(id).orElseThrow();
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Was invoked method for uploadAvatar");
        Student student = findStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
    }

    private String getExtension(String fileName) {
        logger.info("Was invoked method for getExtension");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}

