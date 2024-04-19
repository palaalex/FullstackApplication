package com.andres.curso.springboot.app.springbootcrud;

import com.andres.curso.springboot.app.springbootcrud.entities.Token;
import com.andres.curso.springboot.app.springbootcrud.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.UUID;

public class JWTGenerator {

    static Algorithm algorithm = Algorithm.HMAC256("baeldung");

    static JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer("Baeldung")
            .build();

    public static String generateFromUser(User user){
        String jwtToken = JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("id", user.getId())
                .withIssuedAt(new Date())
                .withIssuer("Baeldung")
                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .withJWTId(UUID.randomUUID()
                        .toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(algorithm);
        return jwtToken;
    }

    public static Boolean verifyToken(String token) {
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static String getTokenClaim(String token, String claim) {
        DecodedJWT decodedJWT = null;
        try{
            decodedJWT = verifier.verify(token);
            System.out.println(decodedJWT.getClaim("username").asString());
            return decodedJWT.getClaim(claim).asString();
        } catch (JWTVerificationException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
