package io.github.radd.mgrgpsserver.controller;

import io.github.radd.mgrgpsserver.domain.model.Location;
import io.github.radd.mgrgpsserver.domain.model.Track;
import io.github.radd.mgrgpsserver.domain.repository.LocationRepository;
import io.github.radd.mgrgpsserver.domain.repository.TrackRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class WebSocketController {


    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    TrackRepository trackRepo;

    @Autowired
    LocationRepository locationRepo;

    @MessageMapping("/send/{fromID}")
    public void processMsg(@DestinationVariable String fromID, @Payload Location location,
                           Authentication auth) {
        //System.out.println("From id:" + fromID);
        //System.out.println(auth.getPrincipal());

        //System.out.println("Delay: " + (System.currentTimeMillis() - location.getTimestamp()));

        String userID = (String) auth.getPrincipal(); // userID - saved in JwtAuthenticationFilter

        if(!userID.equals(location.getUserID()) || !userID.equals(fromID))
            return;

        template.convertAndSend("/topic/get/"+fromID, location);

        if(location.getTrackID() == null || location.getTrackID().isEmpty())
            return;

        Track track = trackRepo.findBy_id(new ObjectId(location.getTrackID()));
        if(track == null)
            return;

        if(track.getFinishTimestamp() != 0) // save location only when track is open
            return;

        locationRepo.save(location);

    }

}
