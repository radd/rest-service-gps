package io.github.radd.mgrgpsserver.webapi;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

public class UserResponse {

    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId _id;
    private String email;
    private String username;
    private long joinTimestamp;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getJoinTimestamp() {
        return joinTimestamp;
    }

    public void setJoinTimestamp(long joinTimestamp) {
        this.joinTimestamp = joinTimestamp;
    }
}
