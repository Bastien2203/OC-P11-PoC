package fr.medhead.emergency_bed_service.controller;

import fr.medhead.emergency_bed_service.model.Hospital;
import fr.medhead.emergency_bed_service.repository.HospitalRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class HospitalDecrementIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    private Hospital hospitalWithBeds;
    private Hospital hospitalEmpty;

    @BeforeEach
    void setUp() {
        hospitalRepository.deleteAll();

        Hospital h1 = new Hospital();
        h1.setName("Hôpital A");
        h1.setAvailableBeds(1);
        h1.setLatitude(0.0);
        h1.setLongitude(0.0);
        hospitalWithBeds = hospitalRepository.save(h1);

        Hospital h2 = new Hospital();
        h2.setName("Hôpital B");
        h2.setAvailableBeds(0);
        h2.setLatitude(0.0);
        h2.setLongitude(0.0);
        hospitalEmpty = hospitalRepository.save(h2);
    }

    @Test
    public void shouldSuccessfullyDecrementBedWhenAvailable() throws Exception {
        mockMvc.perform(patch("/api/hospitals/{id}/decrement", hospitalWithBeds.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableBeds").value(0));
    }

    @Test
    public void shouldFailToDecrementBedWhenZeroBedsAvailable() throws Exception {
        mockMvc.perform(patch("/api/hospitals/{id}/decrement", hospitalEmpty.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
}