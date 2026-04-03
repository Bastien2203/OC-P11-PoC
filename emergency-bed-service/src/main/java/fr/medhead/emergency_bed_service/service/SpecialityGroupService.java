package fr.medhead.emergency_bed_service.service;

import fr.medhead.emergency_bed_service.model.SpecialityGroup;
import fr.medhead.emergency_bed_service.repository.SpecialityGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityGroupService {
    private final SpecialityGroupRepository repository;

    public SpecialityGroupService(SpecialityGroupRepository repository) {
        this.repository = repository;
    }

    public List<SpecialityGroup> getAllGroups() {
        return repository.findAll();
    }
}
