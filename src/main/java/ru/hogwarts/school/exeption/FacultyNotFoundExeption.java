package ru.hogwarts.school.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FacultyNotFoundExeption extends RuntimeException{

    private final Long id;
    public FacultyNotFoundExeption(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
