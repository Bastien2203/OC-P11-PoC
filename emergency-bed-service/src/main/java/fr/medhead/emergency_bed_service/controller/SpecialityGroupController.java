package fr.medhead.emergency_bed_service.controller;

import fr.medhead.emergency_bed_service.model.SpecialityGroup;
import fr.medhead.emergency_bed_service.service.SpecialityGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Speciality Groups")
@RequestMapping("/api/speciality-groups")
public class SpecialityGroupController {
    private final SpecialityGroupService service;

    public SpecialityGroupController(SpecialityGroupService service) {
        this.service = service;
    }

    @Operation(summary = "Get all speciality groups, with their nested specialities")
    @GetMapping
    public List<SpecialityGroup> getAllGroups() {
        return service.getAllGroups();
    }
}
