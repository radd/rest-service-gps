package io.github.radd.mgrgpsserver.webapi;

import io.github.radd.mgrgpsserver.domain.model.Location;
import io.github.radd.mgrgpsserver.domain.model.Track;

import java.util.List;

public class TrackFullInfoResponse {
    private Track track;
    private UserResponse user;
    private List<Location> locations;

    public TrackFullInfoResponse(Track track, UserResponse user, List<Location> locations) {
        this.track = track;
        this.user = user;
        this.locations = locations;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
