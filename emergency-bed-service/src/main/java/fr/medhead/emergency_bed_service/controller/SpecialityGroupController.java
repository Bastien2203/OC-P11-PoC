package fr.medhead.emergency_bed_service.controller;

import fr.medhead.emergency_bed_service.model.SpecialityGroup;
import fr.medhead.emergency_bed_service.service.SpecialityGroupService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/speciality-groups")
@CrossOrigin(origins = "http://localhost:5173")
public class SpecialityGroupController {
    private final SpecialityGroupService service;

    public SpecialityGroupController(SpecialityGroupService service) {
        this.service = service;
    }

    @GetMapping
    public List<SpecialityGroup> getAllGroups() {
        return service.getAllGroups();
    }
}
