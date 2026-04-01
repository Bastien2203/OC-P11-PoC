package fr.medhead.emergency_bed_service.repository;

import fr.medhead.emergency_bed_service.model.SpecialityGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityGroupRepository extends JpaRepository<SpecialityGroup, Long> {
}
