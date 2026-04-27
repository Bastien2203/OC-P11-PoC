package fr.medhead.emergency_bed_service.service;

import fr.medhead.emergency_bed_service.dto.HospitalRoute;
import fr.medhead.emergency_bed_service.model.Hospital;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HospitalRoutingService {

    private static final String OSRM_TABLE_URL = "http://router.project-osrm.org/table/v1/driving/{coordinates}?sources=0&annotations=distance";

    private final RestTemplate restTemplate;

    public HospitalRoutingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<HospitalRoute> getHospitalRoutes(double lat1, double lon1, List<Hospital> hospitals) {
        if (hospitals == null || hospitals.isEmpty()) {
            return Collections.emptyList();
        }

        String url = buildUrl(lat1, lon1, hospitals);

        try {
            OsrmTableResponse response = restTemplate.getForObject(url, OsrmTableResponse.class);

            if (response != null && "Ok".equals(response.code()) && response.distances() != null && !response.distances().isEmpty()) {
                return buildRoutes(hospitals, response);
            } else {
                throw new RuntimeException("No route found by OSRM.");
            }
        } catch (Exception e) {
            throw new RuntimeException("OSRM call failed", e);
        }
    }

    private List<HospitalRoute> buildRoutes(List<Hospital> hospitals, OsrmTableResponse response) {
        List<Double> distancesFromSource = response.distances().getFirst();
        List<HospitalRoute> hospitalRoutes = new ArrayList<>();

        for (int i = 1; i < distancesFromSource.size(); i++) {
            Hospital currentHospital = hospitals.get(i - 1);
            Double distanceInMeters = distancesFromSource.get(i);

            Double distanceInKm = null;
            if (distanceInMeters != null) {
                distanceInKm = distanceInMeters / 1000.0;
            }

            hospitalRoutes.add(new HospitalRoute(currentHospital, distanceInKm));
        }
        return hospitalRoutes;
    }

    private String buildUrl(double lat1, double lon1, List<Hospital> hospitals) {
        StringBuilder coordsBuilder = new StringBuilder();
        coordsBuilder.append(lon1).append(",").append(lat1);

        for (Hospital hospital : hospitals) {
            double hLon = hospital.getLongitude();
            double hLat = hospital.getLatitude();
            coordsBuilder.append(";").append(hLon).append(",").append(hLat);
        }

        return OSRM_TABLE_URL.replace("{coordinates}", coordsBuilder.toString());
    }

    private record OsrmTableResponse(String code, List<List<Double>> distances) {
    }
}