package com.backend.blog.services;

import com.backend.blog.DTOs.DTOUser;
import com.backend.blog.DTOs.DTOUserUpdated;
import com.backend.blog.models.User;
import com.backend.blog.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public DTOUser changePasswordByTokenJWT(DTOUserUpdated data, HttpServletRequest request)
    {
        String token = jwtService.getHeader(request);
        String getUsername = jwtService.verifyToken(token);
        User user = userRepository.findByUsername(getUsername);
        user.changePassword(new BCryptPasswordEncoder().encode(data.password()));
        return new DTOUser(user.getUsername(), user.getPassword());
    }
}
