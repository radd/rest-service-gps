package io.github.radd.mgrgpsserver.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "track")
public class Track {

    @JsonSerialize(using= ToStringSerializer.class)
    @Id
    private ObjectId _id;

    private String name;

    @Indexed
    private String userID;

    private long startTimestamp;
    private long finishTimestamp;

    private float distance;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getFinishTimestamp() {
        return finishTimestamp;
    }

    public void setFinishTimestamp(long finishTimestamp) {
        this.finishTimestamp = finishTimestamp;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

}
