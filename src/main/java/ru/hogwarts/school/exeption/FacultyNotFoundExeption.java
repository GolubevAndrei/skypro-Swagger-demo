package ru.hogwarts.school.exeption;

public class FacultyNotFoundExeption extends RuntimeException{

    private final Long id;
    public FacultyNotFoundExeption(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
