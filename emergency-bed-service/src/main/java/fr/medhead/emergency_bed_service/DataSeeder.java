package fr.medhead.emergency_bed_service;

import fr.medhead.emergency_bed_service.model.Speciality;
import fr.medhead.emergency_bed_service.model.SpecialityGroup;
import fr.medhead.emergency_bed_service.repository.HospitalRepository;
import fr.medhead.emergency_bed_service.repository.SpecialityGroupRepository;
import fr.medhead.emergency_bed_service.repository.SpecialityRepository;
import fr.medhead.emergency_bed_service.service.HospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
@Profile("seed")
public class DataSeeder implements CommandLineRunner {
    private final HospitalService hospitalService;
    private final HospitalRepository hospitalRepository;
    private final SpecialityRepository specialityRepo;
    private final SpecialityGroupRepository groupRepo;


    public DataSeeder(
            HospitalService hospitalService,
            SpecialityRepository specialityRepo,
            SpecialityGroupRepository groupRepo,
            HospitalRepository hospitalRepository
    ) {
        this.hospitalService = hospitalService;
        this.specialityRepo = specialityRepo;
        this.groupRepo = groupRepo;
        this.hospitalRepository = hospitalRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        clearData();
        loadSpecialities();
        loadHospitals();
    }

    private void loadHospitals() {
        hospitalService.createHospital(
                "Hôpital Fred Brooks",
                2,
                List.of("Cardiologie", "Immunologie"),
                46.890542, 0.4611285
        );

        hospitalService.createHospital(
                "Hôpital Julia Crusher",
                0,
                List.of("Cardiologie"),
                47.2124144, 2.5320513
        );

        hospitalService.createHospital(
                "Hôpital Beverly Bashir",
                5,
                List.of("Immunologie", "Neuropathologie"),
                46.4677744, -1.1239787
        );
    }


    private void loadSpecialities() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String, List<String>>> typeReference = new TypeReference<>() {
        };
        try (InputStream inputStream = getClass().getResourceAsStream("/specialities.json")) {
            if (inputStream == null) {
                System.err.println("Failed to read specialities.json");
                return;
            }

            Map<String, List<String>> specialityData = mapper.readValue(inputStream, typeReference);
            for (Map.Entry<String, List<String>> entry : specialityData.entrySet()) {
                String groupName = entry.getKey();
                List<String> specialitiesList = entry.getValue();

                SpecialityGroup group = new SpecialityGroup();
                group.setName(groupName);
                groupRepo.save(group);

                for (String specialityName : specialitiesList) {
                    Speciality speciality = new Speciality();
                    speciality.setName(specialityName);
                    speciality.setGroup(group);

                    specialityRepo.save(speciality);
                }
            }
        }

        System.out.println("Successfully loaded specialities.json");

    }


    private void clearData() {
        hospitalRepository.deleteAll();
        groupRepo.deleteAll();
        specialityRepo.deleteAll();
    }
}
