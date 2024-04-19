package com.andres.curso.springboot.app.springbootcrud.controllers;

import com.andres.curso.springboot.app.springbootcrud.JWTGenerator;
import com.andres.curso.springboot.app.springbootcrud.PasswordEncrypter;
import com.andres.curso.springboot.app.springbootcrud.entities.User;
import com.andres.curso.springboot.app.springbootcrud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.andres.curso.springboot.app.springbootcrud.entities.Token;

import java.security.NoSuchAlgorithmException;

import static com.andres.curso.springboot.app.springbootcrud.PasswordEncrypter.getSHA;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @PostMapping(path = "/register")
    public @ResponseBody ResponseEntity<?> register(@RequestBody User user) throws NoSuchAlgorithmException {
        String password = PasswordEncrypter.toHexString(getSHA(user.getPassword()));
        user.setPassword(password);
        userRepository.save(user);
        Token token = new Token();
        token.setToken(JWTGenerator.generateFromUser(user));
        return ResponseEntity.ok(token);
    }

    @PostMapping(path = "/login")
    public @ResponseBody ResponseEntity<?> getToken(@RequestBody User user) {
        try{
            String password = PasswordEncrypter.toHexString(getSHA(user.getPassword()));
            User newUser = userRepository.findByUsernameAndPassword(user.getUsername(), password);
            if(newUser != null) {
                Token token = new Token();
                token.setToken(JWTGenerator.generateFromUser(newUser));
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(path = "/verify")
    public @ResponseBody ResponseEntity<?> verify(@RequestHeader String token) {
        Boolean isVerified = JWTGenerator.verifyToken(token);
        if (isVerified) {
            return ResponseEntity.ok("Token verified");
        }
        return ResponseEntity.badRequest().body("Token verification failed");
    }


}
