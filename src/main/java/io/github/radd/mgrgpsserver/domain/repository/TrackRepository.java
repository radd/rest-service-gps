package io.github.radd.mgrgpsserver.domain.repository;

import io.github.radd.mgrgpsserver.domain.model.Track;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TrackRepository extends MongoRepository<Track, ObjectId> {
    Track findBy_id(ObjectId _id);
    List<Track> findAllByUserIDOrderByStartTimestampDesc(String userID);
}