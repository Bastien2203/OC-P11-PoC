package fr.medhead.emergency_bed_service.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer availableBeds;

    @JsonIgnore
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point coordinate;

    @JsonProperty("latitude")
    public Double getLatitude() {
        return coordinate != null ? coordinate.getY() : null;
    }

    @JsonProperty("longitude")
    public Double getLongitude() {
        return coordinate != null ? coordinate.getX() : null;
    }

    @ManyToMany
    @JoinTable(
            name = "hospital_speciality",
            joinColumns = @JoinColumn(name = "hospital_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private List<Speciality> specialities = new ArrayList<>();
}
