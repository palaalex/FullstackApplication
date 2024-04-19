package com.andres.curso.springboot.app.springbootcrud.controllers;

import com.andres.curso.springboot.app.springbootcrud.JWTGenerator;
import com.andres.curso.springboot.app.springbootcrud.entities.Post;
import com.andres.curso.springboot.app.springbootcrud.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping(path = "/posts")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping(path = "/all")
    private ResponseEntity<?> getAllPosts(@RequestHeader("Token") String token) {
        if (JWTGenerator.verifyToken(token)) {
            return ResponseEntity.ok(postRepository.findAll());
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping(path = "/{id}")
    private @ResponseBody ResponseEntity<?> getPostById(@PathVariable("id") int id, @RequestHeader("Token") String token) {
        if (JWTGenerator.verifyToken(token)) {
            try {
                Post post = postRepository.findById(id).get();
                return ResponseEntity.ok(post);
            } catch (NoSuchElementException e) {
                return ResponseEntity.status(404).build();
            }
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping(path = "/")
    @ResponseBody
    private ResponseEntity<?> addPost(@RequestBody Post post, @RequestHeader("Token") String token) {
        if (JWTGenerator.verifyToken(token)) {
            post.setCreatedOn(new Date(System.currentTimeMillis()));
            post.setUpdatedOn(new Date(System.currentTimeMillis()));
            postRepository.save(post);
            return ResponseEntity.ok("Post created successfully");
        }
        return ResponseEntity.status(401).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updatePost(@PathVariable("id") int id, @RequestBody Post post, @RequestHeader("Token") String token) {
        if (JWTGenerator.verifyToken(token)) {
            try {
                Post updatedPost = postRepository.findById(id).get();
                updatedPost.setTitle(post.getTitle());
                updatedPost.setContent(post.getContent());
                updatedPost.setUpdatedOn(new Date(System.currentTimeMillis()));
                postRepository.save(updatedPost);
                return ResponseEntity.ok("Post updated successfully");
            } catch (NoSuchElementException e) {
                return ResponseEntity.status(404).build();
            }
        }
        return ResponseEntity.status(401).build();
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id, @RequestHeader("Token") String token) {
        if (JWTGenerator.verifyToken(token)) {
            try {
                postRepository.deleteById(id);
                return ResponseEntity.ok("Post deleted successfully");
            } catch (NoSuchElementException e) {
                return ResponseEntity.status(404).build();
            }
        }
        return ResponseEntity.status(401).build();
    }
}
