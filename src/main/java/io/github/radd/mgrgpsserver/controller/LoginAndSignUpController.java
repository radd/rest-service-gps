package io.github.radd.mgrgpsserver.controller;

import io.github.radd.mgrgpsserver.domain.repository.UserRepository;
import io.github.radd.mgrgpsserver.security.JwtTokenProvider;
import io.github.radd.mgrgpsserver.service.UserService;
import io.github.radd.mgrgpsserver.utils.ObjectMapperUtils;
import io.github.radd.mgrgpsserver.webapi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/")
public class LoginAndSignUpController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest signUpRequest,
                                    BindingResult result) {
        UserResponse userResponse = null;

        if(result.hasErrors())
            return ResponseEntity.badRequest().body(new MessageResponse(false,
                    "Validation error", result.getAllErrors()));

        try {
            userResponse = userService.signUp(signUpRequest);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new MessageResponse(false,
                    e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(true,
                "Sign up successful", userResponse));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // not necessary
        //SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        UserResponse user = ObjectMapperUtils.map(
                                userRepo.findByEmail(loginRequest.getEmail()).orElse(null),
                                UserResponse.class);

        JwtAuthenticationResponse response  = new JwtAuthenticationResponse(jwt, user);

        return ResponseEntity.ok(new MessageResponse(true, "Login succeeded", response));
    }


}
