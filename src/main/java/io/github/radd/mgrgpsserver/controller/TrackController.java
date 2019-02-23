package io.github.radd.mgrgpsserver.controller;

import io.github.radd.mgrgpsserver.domain.model.Location;
import io.github.radd.mgrgpsserver.domain.model.Track;
import io.github.radd.mgrgpsserver.domain.model.User;
import io.github.radd.mgrgpsserver.domain.repository.LocationRepository;
import io.github.radd.mgrgpsserver.domain.repository.TrackRepository;
import io.github.radd.mgrgpsserver.domain.repository.UserRepository;
import io.github.radd.mgrgpsserver.service.TrackService;
import io.github.radd.mgrgpsserver.utils.ObjectMapperUtils;
import io.github.radd.mgrgpsserver.webapi.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class TrackController {

    @Autowired
    LocationRepository locationRepo;

    @Autowired
    TrackRepository trackRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    TrackService trackService;

    @GetMapping("/track/{trackID}/full")
    public ResponseEntity<?> getTrack(@PathVariable String trackID) {
        //System.out.println(trackID);

        Track track = trackRepo.findBy_id(new ObjectId(trackID));
        if(track == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse(false, "Track not found"));

        User user = userRepo.findBy_id(new ObjectId(track.getUserID()));

        List<Location> allLoc = locationRepo.findAllByTrackID(trackID);

        TrackFullInfoResponse response =
                new TrackFullInfoResponse(
                        track,
                        ObjectMapperUtils.map(user, UserResponse.class),
                        allLoc);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/track/{trackID}/locations")
    public ResponseEntity<?> getTrackLocs(@PathVariable String trackID) {
        //System.out.println(trackID);

        List<Location> allLoc = locationRepo.findAllByTrackID(trackID);

        return ResponseEntity.ok().body(allLoc);
    }

    @PostMapping("/track/create_track")
    public ResponseEntity<?> createTrack(@Valid @RequestBody TrackRequest trackRequest,
                                         Authentication auth) {
        //System.out.println(auth.getPrincipal());
        String userID = (String) auth.getPrincipal();

        Track track = new Track();
        track.setUserID(userID);
        track.setName(trackRequest.getName());
        track.setStartTimestamp(System.currentTimeMillis());

        Track newTrack = trackRepo.save(track);

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(true,
                "Track successful created", newTrack));
    }

    @PostMapping("/track/finish_track")
    public ResponseEntity<?> finishTrack(@RequestBody TrackFinishRequest trackRequest,
                                         Authentication auth) {
        //System.out.println(auth.getPrincipal());
        String userID = (String) auth.getPrincipal();
        String trackID = trackRequest.getTrackID();

        Track track = trackRepo.findBy_id(new ObjectId(trackID));

        if(track == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");

        if(!track.getUserID().equals(userID))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");

        if(track.getFinishTimestamp() != 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");

        track.setFinishTimestamp(System.currentTimeMillis());

        track.setDistance(trackService.calculateTrackDistance(track));
        trackRepo.save(track);

        return ResponseEntity.ok().body(new MessageResponse(true,
                "Track finished", ""));
    }

}
