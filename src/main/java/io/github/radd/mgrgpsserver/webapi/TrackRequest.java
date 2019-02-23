package io.github.radd.mgrgpsserver.webapi;

import javax.validation.constraints.NotBlank;

public class TrackRequest {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
