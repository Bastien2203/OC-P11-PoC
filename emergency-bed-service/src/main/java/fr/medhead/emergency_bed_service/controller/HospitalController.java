package fr.medhead.emergency_bed_service.controller;


import fr.medhead.emergency_bed_service.model.Hospital;
import fr.medhead.emergency_bed_service.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@Tag(name = "Hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @Operation(summary = "Get all existing hospitals")
    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    @Operation(
            summary = "Get the nearest hospital available for a speciality",
            description = "Find the nearest hospital with available beds and selected speciality, depending on the coordinates passed by params. The distance is evaluated using OSRM"
    )
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


    @Operation(summary = "Decrement bed availabilty of one, for the specified hospital")
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
