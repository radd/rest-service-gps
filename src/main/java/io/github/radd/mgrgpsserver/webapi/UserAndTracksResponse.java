package io.github.radd.mgrgpsserver.webapi;

import io.github.radd.mgrgpsserver.domain.model.Track;

import java.util.List;

public class UserAndTracksResponse {

    private UserResponse user;
    private List<Track> trackList;

    public UserAndTracksResponse(UserResponse user, List<Track> trackList) {
        this.user = user;
        this.trackList = trackList;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }
}
