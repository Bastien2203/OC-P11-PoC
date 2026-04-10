package fr.medhead.emergency_bed_service.repository;

import fr.medhead.emergency_bed_service.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    @Query(value = """
            SELECT *
            FROM hospital h, hospital_speciality hs
            WHERE hs.hospital_id = h.id
            AND hs.speciality_id = :speciality_id
            AND h.available_beds > 1
            ORDER BY h.coordinate <-> ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)
            LIMIT 1
            """, nativeQuery = true)
    Hospital findNearest(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("speciality_id") int specialityId
    );
}

