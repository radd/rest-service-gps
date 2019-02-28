package io.github.radd.mgrgpsserver.utils;

import io.github.radd.mgrgpsserver.domain.model.Location;
import io.github.radd.mgrgpsserver.domain.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SimulateGPSClients {

    class ClientInfo {
        int ID;
        String clientID;
        String trackID;
        List<Location> locations;
        int nextIndex = -1;

        public ClientInfo(int ID, String clientID, String trackID) {
            this.ID = ID;
            this.clientID = clientID;
            this.trackID = trackID;
        }

        int getNext() {
            nextIndex = locations.size() - 1 > nextIndex ? nextIndex + 1 : Integer.MAX_VALUE;
            return nextIndex;
        }
    }

    public boolean isActive = true;

    final SimpMessagingTemplate template;

    final LocationRepository locationRepo;

    List<ClientInfo> clients;

    final TaskScheduler taskExecutor;


    @Autowired
    public SimulateGPSClients(LocationRepository locationRepo,
                              SimpMessagingTemplate template,
                              @Qualifier("taskScheduler") TaskScheduler taskExecutor) {
        this.locationRepo = locationRepo;
        this.template = template;
        this.taskExecutor = taskExecutor;

        createClients();
    }

    private void createClients() {
        clients = new ArrayList<>();

        clients.add(new ClientInfo(1,"5c5d6da932bee21b60fca64b", "5c77c19e548dc69cf7990567"));
        clients.add(new ClientInfo(2,"5c5d6f9232bee21b60fca64c", "5c778836548dc69cf79903f7"));
        clients.add(new ClientInfo(3,"5c5ed4bc32bee237b824bbb8", "5c738781548dc69cf798f940"));
        clients.add(new ClientInfo(4,"5c5ed13732bee237b824bbb6", "5c72863d548dc69cf798f843"));
        clients.add(new ClientInfo(5,"5c5ed1f432bee237b824bbb7", "5c6fbcbf32bee219a40f752c"));
        clients.add(new ClientInfo(6,"5c5eb09032bee23be84a2d9a", "5c752e56548dc69cf79902b9"));


        clients.forEach(clientInfo -> {
            clientInfo.locations = locationRepo.findAllByTrackID(clientInfo.trackID);
        });

       startSimulation();
    }

    public void resetClients() {
        clients.forEach(clientInfo -> {
            clientInfo.nextIndex = 0;
        });
    }

    private void startSimulation() {
        clients.forEach(clientInfo -> {
            scheduling((Runnable) () -> {
                if(!isActive || clientInfo.locations.size() == 0)
                    return;

                int index = clientInfo.getNext();
                if(index == Integer.MAX_VALUE)
                    return;

                Location location = clientInfo.locations.get(index);
                sendMessage(clientInfo.clientID, location);

            },clientInfo.ID * 600);

        });
    }

    private void scheduling(final Runnable task, int initialDelay) {
        taskExecutor.scheduleWithFixedDelay(task,
                Date.from(LocalDateTime.now().plusNanos(initialDelay * 1000000)
                        .atZone(ZoneId.systemDefault()).toInstant()),
                3000);
    }


    private void sendMessage(String clientID, Location loc) {
        loc.setUserID(clientID);
        loc.setTimestamp(System.currentTimeMillis());
        template.convertAndSend("/topic/get/" + clientID, loc);
    }



}
