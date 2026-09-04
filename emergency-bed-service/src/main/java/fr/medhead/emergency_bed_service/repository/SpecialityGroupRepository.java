package fr.medhead.emergency_bed_service.repository;

import fr.medhead.emergency_bed_service.model.SpecialityGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialityGroupRepository extends JpaRepository<SpecialityGroup, Long> {


    @Query("SELECT g FROM SpecialityGroup g LEFT JOIN FETCH g.specialities")
    List<SpecialityGroup> findAllWithSpecialities();
}
