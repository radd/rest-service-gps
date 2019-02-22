package io.github.radd.mgrgpsserver.service;

import io.github.radd.mgrgpsserver.domain.model.User;
import io.github.radd.mgrgpsserver.domain.repository.UserRepository;
import io.github.radd.mgrgpsserver.utils.ObjectMapperUtils;
import io.github.radd.mgrgpsserver.webapi.SignUpRequest;
import io.github.radd.mgrgpsserver.webapi.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserResponse signUp(SignUpRequest signUpRequest) {
        Assert.notNull(signUpRequest, "User Request NULL");

        Assert.isTrue(signUpRequest.getPassword().equals(signUpRequest.getPassword2()),
                "Passwords dont match");

        Assert.isTrue(!emailExists(signUpRequest.getEmail()), "Email exists");

        User user = saveUser(signUpRequest);

        Assert.notNull(user, "Save failed");

        UserResponse userResponse = ObjectMapperUtils.map(user, UserResponse.class);

        return userResponse;
    }

    private User saveUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setUsername(signUpRequest.getUsername());
        user.setJoinTimestamp(System.currentTimeMillis());

        return userRepo.save(user);
    }

    private boolean emailExists(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null;
    }

}
