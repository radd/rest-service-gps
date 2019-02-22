package io.github.radd.mgrgpsserver.domain.repository;

import io.github.radd.mgrgpsserver.domain.model.Location;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocationRepository extends MongoRepository<Location, ObjectId> {
    Location findBy_id(ObjectId _id);
    List<Location> findAllByTrackID(String trackID);
}