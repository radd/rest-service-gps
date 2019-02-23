package io.github.radd.mgrgpsserver.utils;

import io.github.radd.mgrgpsserver.domain.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SimulateGPSClients {

    @Autowired
    SimpMessagingTemplate template;

    private final Map<String, Location> locations = new ConcurrentHashMap<>();

    public SimulateGPSClients() {
        createClients();
    }

    private void createClients() {
        createClient1();
        createClient2();
        createClient3();
        createClient4();
        createClient5();
        createClient6();
    }

    public void resetClients() {
        createClients();
    }

    private void createClient1() {
        String ID = "5c5d6da932bee21b60fca64b";
        Location loc = new Location();
        loc.setAltitude(0);
        loc.setLongitude("17.85");
        loc.setLatitude("50.21");
        loc.setSpeed(0);
        loc.setAccuracy(2);
        loc.setTimestamp(System.currentTimeMillis());
        loc.setTrackID("0");
        loc.setUserID(ID);
        locations.put(ID, loc);
    }

    private void createClient2() {
        String ID = "5c5d6f9232bee21b60fca64c";
        Location loc = new Location();
        loc.setAltitude(0);
        loc.setLongitude("17.86");
        loc.setLatitude("50.22");
        loc.setSpeed(0);
        loc.setTimestamp(System.currentTimeMillis());
        loc.setTrackID("0");
        loc.setUserID(ID);
        locations.put(ID, loc);
    }

    private void createClient3() {
        String ID = "5c5eb09032bee23be84a2d9a";
        Location loc = new Location();
        loc.setAltitude(0);
        loc.setLongitude("17.83");
        loc.setLatitude("50.20");
        loc.setSpeed(0);
        loc.setTimestamp(System.currentTimeMillis());
        loc.setTrackID("0");
        loc.setUserID(ID);
        locations.put(ID, loc);
    }

    private void createClient4() {
        String ID = "5c5ed13732bee237b824bbb6";
        Location loc = new Location();
        loc.setAltitude(0);
        loc.setLongitude("17.84");
        loc.setLatitude("50.201");
        loc.setSpeed(0);
        loc.setTimestamp(System.currentTimeMillis());
        loc.setTrackID("0");
        loc.setUserID(ID);
        locations.put(ID, loc);
    }

    private void createClient5() {
        String ID = "5c5ed1f432bee237b824bbb7";
        Location loc = new Location();
        loc.setAltitude(0);
        loc.setLongitude("17.85");
        loc.setLatitude("50.209");
        loc.setSpeed(0);
        loc.setTimestamp(System.currentTimeMillis());
        loc.setTrackID("0");
        loc.setUserID(ID);
        locations.put(ID, loc);
    }

    private void createClient6() {
        String ID = "5c5ed4bc32bee237b824bbb8";
        Location loc = new Location();
        loc.setAltitude(0);
        loc.setLongitude("17.83");
        loc.setLatitude("50.192");
        loc.setSpeed(0);
        loc.setTimestamp(System.currentTimeMillis());
        loc.setTrackID("0");
        loc.setUserID(ID);
        locations.put(ID, loc);
    }

    private void sendMessage(Location loc) {
        loc.setSpeed(Math.round(Math.random()*100));
        template.convertAndSend("/topic/get/" + loc.getUserID(), loc);
    }


    @Scheduled(fixedDelay = 1000, initialDelay = 100)
    public void simulateClient1() {
        Location loc = locations.get("5c5d6da932bee21b60fca64b");
        loc.setTimestamp(System.currentTimeMillis());

        String lat = String.valueOf(Double.parseDouble(loc.getLatitude()) + (Math.random()*0.0001));
        String lon = String.valueOf(Double.parseDouble(loc.getLongitude()) + (Math.random()*0.0001));
        loc.setLatitude(lat);
        loc.setLongitude(lon);

        sendMessage(loc);
    }



    @Scheduled(fixedDelay = 1000, initialDelay = 200)
    public void simulateClient2() {
        Location loc = locations.get("5c5d6f9232bee21b60fca64c");
        loc.setTimestamp(System.currentTimeMillis());

        String lat = String.valueOf(Double.parseDouble(loc.getLatitude()) + (Math.random()*0.0002));
        String lon = String.valueOf(Double.parseDouble(loc.getLongitude()) + (Math.random()*0.0002));
        loc.setLatitude(lat);
        loc.setLongitude(lon);

        sendMessage(loc);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 400)
    public void simulateClient3() {
        Location loc = locations.get("5c5eb09032bee23be84a2d9a");
        loc.setTimestamp(System.currentTimeMillis());

        String lat = String.valueOf(Double.parseDouble(loc.getLatitude()) + (Math.random()*0.00014));
        String lon = String.valueOf(Double.parseDouble(loc.getLongitude()) + (Math.random()*0.00014));
        loc.setLatitude(lat);
        loc.setLongitude(lon);

        sendMessage(loc);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void simulateClient4() {
        Location loc = locations.get("5c5ed13732bee237b824bbb6");
        loc.setTimestamp(System.currentTimeMillis());

        String lat = String.valueOf(Double.parseDouble(loc.getLatitude()) + (Math.random()*0.00011));
        String lon = String.valueOf(Double.parseDouble(loc.getLongitude()) + (Math.random()*0.00011));
        loc.setLatitude(lat);
        loc.setLongitude(lon);

        sendMessage(loc);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 700)
    public void simulateClient5() {
        Location loc = locations.get("5c5ed1f432bee237b824bbb7");
        loc.setTimestamp(System.currentTimeMillis());

        String lat = String.valueOf(Double.parseDouble(loc.getLatitude()) + (Math.random()*0.00015));
        String lon = String.valueOf(Double.parseDouble(loc.getLongitude()) + (Math.random()*0.00015));
        loc.setLatitude(lat);
        loc.setLongitude(lon);

        sendMessage(loc);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 800)
    public void simulateClient6() {
        Location loc = locations.get("5c5ed4bc32bee237b824bbb8");
        loc.setTimestamp(System.currentTimeMillis());

        String lat = String.valueOf(Double.parseDouble(loc.getLatitude()) + (Math.random()*0.00017));
        String lon = String.valueOf(Double.parseDouble(loc.getLongitude()) + (Math.random()*0.00011));
        loc.setLatitude(lat);
        loc.setLongitude(lon);

        sendMessage(loc);
    }


}
