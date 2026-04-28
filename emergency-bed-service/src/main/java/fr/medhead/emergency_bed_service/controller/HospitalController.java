package fr.medhead.emergency_bed_service.controller;


import fr.medhead.emergency_bed_service.model.Hospital;
import fr.medhead.emergency_bed_service.service.HospitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    @GetMapping("/findNearest")
    public ResponseEntity<Hospital> findNearest(
            @RequestParam(name = "lat") double latitude,
            @RequestParam(name = "lon") double longitude,
            @RequestParam(name = "speciality_id") int specialityId
    ) {
        Hospital hospital = hospitalService.findNearest(latitude, longitude, specialityId);

        if (hospital == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(hospital);
    }


    @PatchMapping("{id}/decrement")
    public ResponseEntity<Hospital> findNearest(
            @PathVariable(name = "id") Long id
    ) {
        Hospital hospital = hospitalService.decrementHospitalBed(id);

        if (hospital == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(hospital);
    }
}
