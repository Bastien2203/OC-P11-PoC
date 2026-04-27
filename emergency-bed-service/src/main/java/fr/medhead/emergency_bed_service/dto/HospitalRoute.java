package fr.medhead.emergency_bed_service.dto;

import fr.medhead.emergency_bed_service.model.Hospital;


public record HospitalRoute(Hospital hospital, Double distanceInKm) {
}