package fr.medhead.emergency_bed_service.repository;

import fr.medhead.emergency_bed_service.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    @Query(value = """
            SELECT *
            FROM hospital h, hospital_speciality hs
            WHERE hs.hospital_id = h.id
            AND hs.speciality_id = :speciality_id
            AND h.available_beds > 1
            """, nativeQuery = true)
    List<Hospital> findAvailableHospitals(
            @Param("speciality_id") int specialityId
    );
}

