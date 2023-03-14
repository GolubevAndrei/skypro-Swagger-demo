package ru.hogwarts.school;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

@WebMvcTest(controllers = FacultyController.class)
@ExtendWith(MockitoExtension.class)
class FacultyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void createTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setColor("Красный");
        faculty.setName("Гриффиндор");

        when(facultyRepository.save(any())).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Faculty facultyRecordResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8),Faculty.class);
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK);
                    assertThat(facultyRecordResult).isNotNull();
                    assertThat(facultyRecordResult).usingRecursiveComparison().ignoringFields("id").isEqualTo(faculty);
                    assertThat(facultyRecordResult.getId()).isEqualTo(faculty.getId());
                });
    }

}

