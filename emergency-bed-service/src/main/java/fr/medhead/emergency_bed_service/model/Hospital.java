package fr.medhead.emergency_bed_service.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "hospital", indexes = {
        @Index(name = "idx_hospital_available_beds", columnList = "available_beds")
})
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(name = "available_beds", nullable = false)
    private Integer availableBeds;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @ManyToMany
    @JoinTable(
            name = "hospital_speciality",
            joinColumns = @JoinColumn(name = "hospital_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"),
            indexes = {
                @Index(name = "idx_speciality_hospital", columnList = "speciality_id, hospital_id")
            }
    )
    private List<Speciality> specialities = new ArrayList<>();

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
