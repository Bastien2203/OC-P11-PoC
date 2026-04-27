package fr.medhead.emergency_bed_service.service;

import fr.medhead.emergency_bed_service.dto.HospitalRoute;
import fr.medhead.emergency_bed_service.model.Hospital;
import fr.medhead.emergency_bed_service.model.Speciality;
import fr.medhead.emergency_bed_service.repository.HospitalRepository;
import fr.medhead.emergency_bed_service.repository.SpecialityRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    private final HospitalRoutingService hospitalRoutingService;

    private final SpecialityRepository specialityRepository;

    private final GeometryFactory geometryFactory = new GeometryFactory(new org.locationtech.jts.geom.PrecisionModel(), 4326);

    public HospitalService(
            HospitalRepository hospitalRepository,
            HospitalRoutingService hospitalRoutingService,
            SpecialityRepository specialityRepository
    ) {
        this.hospitalRepository = hospitalRepository;
        this.specialityRepository = specialityRepository;
        this.hospitalRoutingService = hospitalRoutingService;
    }

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Hospital createHospital(
            String name,
            Integer availableBed,
            List<String> specialities,
            double latitude,
            double longitude
    ) {
        List<Speciality> specialityEntities = new ArrayList<>();
        for (String speciality : specialities) {
            specialityEntities.add(specialityRepository.findByName(speciality));
        }

        Hospital hospital = new Hospital();
        hospital.setName(name);
        hospital.setAvailableBeds(availableBed);
        hospital.setSpecialities(specialityEntities);

        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        hospital.setCoordinate(point);

        return hospitalRepository.save(hospital);
    }


    public Hospital findNearest(double latitude, double longitude, int specialityId) {
        List<Hospital> hospitals = hospitalRepository.findAvailableHospitals(specialityId);

        if (hospitals.isEmpty()) {
            return null;
        }
        List<HospitalRoute> routes = hospitalRoutingService.getHospitalRoutes(latitude, longitude, hospitals);

        return routes.stream()
                .filter(route -> route.distanceInKm() != null)
                .min(Comparator.comparingDouble(HospitalRoute::distanceInKm))
                .map(HospitalRoute::hospital)
                .orElse(null);
    }
}
