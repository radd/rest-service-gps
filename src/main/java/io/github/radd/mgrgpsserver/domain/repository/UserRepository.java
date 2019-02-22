package io.github.radd.mgrgpsserver.domain.repository;

import io.github.radd.mgrgpsserver.domain.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findBy_id(ObjectId _id);
    Optional<User> findByEmail(String email);
}