package fr.medhead.emergency_bed_service.controller;

import fr.medhead.emergency_bed_service.model.Hospital;
import fr.medhead.emergency_bed_service.model.Speciality;
import fr.medhead.emergency_bed_service.model.SpecialityGroup;
import fr.medhead.emergency_bed_service.repository.HospitalRepository;
import fr.medhead.emergency_bed_service.repository.SpecialityGroupRepository;
import fr.medhead.emergency_bed_service.repository.SpecialityRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class HospitalNearestIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private SpecialityGroupRepository specialityGroupRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    private Speciality targetSpeciality;

    @BeforeEach
    void setUp() {
        hospitalRepository.deleteAll();
        specialityRepository.deleteAll();

        SpecialityGroup specialityGroup = new SpecialityGroup();
        specialityGroup.setName("Speciality Group");
        SpecialityGroup group = specialityGroupRepository.save(specialityGroup);

        Speciality spec = new Speciality();
        spec.setName("Urgences");
        spec.setGroup(group);
        targetSpeciality = specialityRepository.save(spec);

        Hospital closeHospital = new Hospital();
        closeHospital.setName("Hôpital Proche");
        closeHospital.setAvailableBeds(5);
        closeHospital.setLatitude(48.8566);
        closeHospital.setLongitude(2.3522);
        closeHospital.setSpecialities(List.of(targetSpeciality));
        hospitalRepository.save(closeHospital);

        Hospital farHospital = new Hospital();
        farHospital.setName("Hôpital Loin");
        farHospital.setAvailableBeds(10);
        farHospital.setLatitude(48.9566);
        farHospital.setLongitude(2.4522);
        farHospital.setSpecialities(List.of(targetSpeciality));
        hospitalRepository.save(farHospital);
    }

    @Test
    public void shouldReturnNearestHospitalWithAvailableBedsAndSpeciality() throws Exception {
        double userLat = 48.8500;
        double userLon = 2.3500;

        mockMvc.perform(get("/api/hospitals/findNearest")
                        .param("lat", String.valueOf(userLat))
                        .param("lon", String.valueOf(userLon))
                        .param("speciality_id", String.valueOf(targetSpeciality.getId()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hôpital Proche"))
                .andExpect(jsonPath("$.availableBeds").value(5));
    }

}
