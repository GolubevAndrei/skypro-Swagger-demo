package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;
@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();

    private long idNumber = 0;
    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(idNumber++);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }
    @Override
    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }
    @Override
    public Faculty editFaculty(long id, Faculty faculty) {
        if (!faculties.containsKey(id)) {
            return null;
        }
        faculties.put(id, faculty);
        return faculty;
    }
    @Override
    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }
}
