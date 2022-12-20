package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exeption.FacultyNotFoundExeption;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }
    @Override
    public Faculty findFaculty(long id) {

        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundExeption(id));
    }
    @Override
    public Faculty editFaculty(long id, Faculty faculty) {
        Faculty oldFaculty = findFaculty(id);
        return facultyRepository.save(oldFaculty);
    }
    @Override
    public Faculty deleteFaculty(long id) {
        Faculty oldFaculty = findFaculty(id);
        facultyRepository.delete(oldFaculty);
        return oldFaculty;
    }
    @Override
    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findAllByColor(color);
    }
}
