package ru.hogwarts.school;

import com.github.javafaker.Faker;
import org.apache.el.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    private final Faker faker = new Faker();

    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    public void  createTest() {
        addStudent(generateStudent(addFaculty(generateFaculty())));

    }

    private Faculty addFaculty(Faculty faculty) {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/faculty",faculty,Faculty.class);
        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(facultyResponseEntity.getBody()).isNotNull();
        assertThat(facultyResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(HttpStatus.OK);
        assertThat(facultyResponseEntity.getBody().getId()).isNotNull();

        return facultyResponseEntity.getBody();
    }

    private Student addStudent(Student student) {
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/students",student,Student.class);
        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentResponseEntity.getBody()).isNotNull();
        assertThat(studentResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(HttpStatus.OK);
        assertThat(studentResponseEntity.getBody().getId()).isNotNull();

        return studentResponseEntity.getBody();
    }

    @Test
    public void putTest() {
        Faculty faculty1 = addFaculty(generateFaculty());
        Faculty faculty2 = addFaculty(generateFaculty());
        Student student = addStudent(generateStudent(faculty1));

        ResponseEntity<Student> getForEntityResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/students/" + student.getId(),Student.class);
        assertThat(getForEntityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getForEntityResponse.getBody()).isNotNull();
        assertThat(getForEntityResponse.getBody()).usingRecursiveComparison().isEqualTo(student);
        assertThat(getForEntityResponse.getBody().getFaculty()).usingRecursiveComparison().isEqualTo(faculty1);

        student.setFaculty(faculty2);

        ResponseEntity<Student> recordResponseEntity = testRestTemplate.exchange("http://localhost:" + port + "/students" + student.getId(), HttpMethod.PUT,new HttpEntity<>(student), Student.class);
        assertThat(getForEntityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getForEntityResponse.getBody()).isNotNull();
        assertThat(getForEntityResponse.getBody()).usingRecursiveComparison().isEqualTo(student);
        assertThat(getForEntityResponse.getBody().getFaculty()).usingRecursiveComparison().isEqualTo(faculty2);
    }

    @Test
    public void findByAgeBetweenTest() {
        List<Faculty> faculties = Stream.generate(this::generateFaculty)
                .limit(5)
                .map(this::addFaculty)
                .toList();
        List<Student> students = Stream.generate(() -> generateStudent(faculties.get(faker.random().nextInt(faculties.size()))))
                .limit(50)
                .map(this::addStudent)
                .toList();

        int minAge = 14;
        int maxAge = 17;

        List<Student> expectedStudent = students.stream()
                .filter(studentRecord -> studentRecord.getAge() >= minAge && studentRecord.getAge() <= maxAge)
                .toList();

        ResponseEntity<List<Student>> getForEntytiresponse = testRestTemplate.exchange("http://localhost:" + port + "/students?minAge={minAge}&maxAge={maxAge}", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Student>>() {
        }, minAge, maxAge);
        assertThat(getForEntytiresponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getForEntytiresponse.getBody()).hasSize(expectedStudent.size()).usingRecursiveFieldElementComparsion();
    }

    private Student generateStudent(Faculty faculty) {
        Student student = new Student();
        student.setName(faker.harryPotter().character());
        student.setAge(faker.random().nextInt(11, 18));
        if (faculty != null) {
            student.setFaculty(faculty);
        }
        return student;
    }
    private Faculty generateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName(faker.harryPotter().character());
        faculty.setColor(faker.color().name());
        return faculty;
    }

}
