package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exeption.FacultyNotFoundExeption;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for addFaculty");
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }
    @Override
    public Faculty findFaculty(long id) {

        logger.info("Was invoked method for findFaculty");
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundExeption(id));
    }
    @Override
    public Faculty editFaculty(long id, Faculty newFaculty) {
        logger.info("Was invoked method for editFaculty");
        Faculty oldFaculty = findFaculty(id);
        oldFaculty.setName(newFaculty.getName());
        oldFaculty.setColor(newFaculty.getColor());
        return facultyRepository.save(oldFaculty);
    }
    @Override
    public Faculty deleteFaculty(long id) {
        logger.info("Was invoked method for deleteFaculty");
        Faculty oldFaculty = findFaculty(id);
        facultyRepository.delete(oldFaculty);
        return oldFaculty;
    }
    @Override
    public Collection<Faculty> findByColor(String color) {

        logger.info("Was invoked method for findByColor");
        return facultyRepository.findAllByColor(color);
    }

    public Collection<Faculty> findByColorOrName(String colorOrName) {
        logger.info("Was invoked method for findByColorOrName");
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(colorOrName, colorOrName);
    }
    @Override
    public Collection<Student> getStudentByFaculty(long id) {
        logger.info("Was invoked method for getStudentByFaculty");
        Faculty faculty = findFaculty(id);
        return studentRepository.findAllByFaculty_Id(faculty.getId());
    }

    public Optional<String> findMaxNameFaculty() {

        logger.info("Was invoked method for findMaxNameFaculty");
        Optional<String> MaxNameFaculty = facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length));

        return MaxNameFaculty;
    }



}
