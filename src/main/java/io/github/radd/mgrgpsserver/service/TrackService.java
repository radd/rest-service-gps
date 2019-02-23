package io.github.radd.mgrgpsserver.service;

import io.github.radd.mgrgpsserver.domain.model.Location;
import io.github.radd.mgrgpsserver.domain.model.Track;
import io.github.radd.mgrgpsserver.domain.repository.LocationRepository;
import org.geotools.referencing.GeodeticCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;

@Service
public class TrackService {

    @Autowired
    LocationRepository locationRepo;

    public float calculateTrackDistance(Track track) {
        ArrayList<Location> allLoc = new ArrayList<>(
                locationRepo.findAllByTrackID(track.get_id().toString()));

        double distance = 0.0;

        for(int i =0; i<allLoc.size()-1; i++) {
            distance += calculateDistance(
                    Double.valueOf(allLoc.get(i).getLongitude()),
                    Double.valueOf(allLoc.get(i).getLatitude()),
                    Double.valueOf(allLoc.get(i+1).getLongitude()),
                    Double.valueOf(allLoc.get(i+1).getLatitude()));
        }

        distance = distance/1000; // km/h
        DecimalFormat df = new DecimalFormat("#.###");

        return Float.valueOf(df.format(distance));
    }

    private double calculateDistance(double a1, double a2, double b1, double b2) {
        GeodeticCalculator geoCalc = new GeodeticCalculator();
        geoCalc.setStartingGeographicPoint(a1, a2);
        geoCalc.setDestinationGeographicPoint(b1, b2);
        return geoCalc.getOrthodromicDistance();
    }
}
