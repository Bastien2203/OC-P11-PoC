package fr.medhead.emergency_bed_service.controller;

import fr.medhead.emergency_bed_service.model.SpecialityGroup;
import fr.medhead.emergency_bed_service.repository.SpecialityGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class SpecialityGroupIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpecialityGroupRepository repository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        SpecialityGroup groupe1 = new SpecialityGroup();
        groupe1.setName("Cardiologie");

        SpecialityGroup groupe2 = new SpecialityGroup();
        groupe2.setName("Neurologie");

        repository.save(groupe1);
        repository.save(groupe2);
    }

    @Test
    public void shouldReturnAllSpecialityGroupsFromRealDatabase() throws Exception {
        mockMvc.perform(get("/api/speciality-groups")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Cardiologie"))
                .andExpect(jsonPath("$[1].name").value("Neurologie"));
    }
}
