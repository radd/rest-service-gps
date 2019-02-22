package io.github.radd.mgrgpsserver.controller;

import io.github.radd.mgrgpsserver.domain.model.Track;
import io.github.radd.mgrgpsserver.domain.model.User;
import io.github.radd.mgrgpsserver.domain.repository.TrackRepository;
import io.github.radd.mgrgpsserver.domain.repository.UserRepository;
import io.github.radd.mgrgpsserver.utils.ObjectMapperUtils;
import io.github.radd.mgrgpsserver.webapi.MessageResponse;
import io.github.radd.mgrgpsserver.webapi.UserAndTracksResponse;
import io.github.radd.mgrgpsserver.webapi.UserResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    TrackRepository trackRepo;

    @GetMapping("/user/{userID}")
    public ResponseEntity<?> getUser(@PathVariable String userID) {
        //System.out.println(userID);

        User user = userRepo.findBy_id(new ObjectId(userID));

        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse(false, "User not found"));

        return ResponseEntity.ok().body(
                ObjectMapperUtils.map(user, UserResponse.class));
    }

    @GetMapping("/user/{userID}/tracks")
    public ResponseEntity<?> getUserTracks(@PathVariable String userID) {
        //System.out.println(userID);

        User user = userRepo.findBy_id(new ObjectId(userID));

        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse(false, "User not found"));

        List<Track> allTracks = trackRepo.findAllByUserIDOrderByStartTimestampDesc(userID);

        UserAndTracksResponse response = new UserAndTracksResponse(
                ObjectMapperUtils.map(user, UserResponse.class),
                allTracks);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(
                ObjectMapperUtils.mapAll(userRepo.findAll(), UserResponse.class));
    }

}
