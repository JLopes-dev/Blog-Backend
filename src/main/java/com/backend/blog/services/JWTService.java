package com.backend.blog.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.backend.blog.DTOs.DTOUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTService {

    @Value("${spring.dotenv.password}")
    private String password;

    public String createTokenJwt(DTOUser data)
    {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            return JWT.create()
                    .withIssuer("Blog")
                    .withSubject(data.username())
                    .withExpiresAt(expiresAt())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Houve um erro ao criar o Token");
        }
    }

    private Instant expiresAt()
    {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }

}
