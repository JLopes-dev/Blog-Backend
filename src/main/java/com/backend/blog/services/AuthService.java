package com.backend.blog.services;

import com.backend.blog.DTOs.DTOUser;
import com.backend.blog.models.User;
import com.backend.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

    public DTOUser createUser(DTOUser data)
    {
        userExists(data);
        String passwordEncoded = new BCryptPasswordEncoder().encode(data.password());
        UserDetails user = userRepository.save(new User(data.username(), passwordEncoded));
        return new DTOUser(user.getUsername(), user.getPassword());
    }

    public String authUser(DTOUser data)
    {
        Authentication authToken = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        authenticationManager.authenticate(authToken);
        return jwtService.createTokenJwt(data);
    }

    public void userExists(DTOUser data)
    {
        if (userRepository.existsByUsername(data.username()))
        {
            throw new IllegalArgumentException("Resource not found.");
        }
    }
}
