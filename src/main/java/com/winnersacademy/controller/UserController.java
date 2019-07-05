package com.winnersacademy.controller;

import com.winnersacademy.entity.User;
import com.winnersacademy.model.UserType;
import com.winnersacademy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("users")
    public ResponseEntity<Page<User>> getAllUsers(@RequestParam(defaultValue = "0", name = "page") Integer pageNumber,
                                                  @RequestParam(defaultValue = "10", name = "size") Integer size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<User> page = userRepository.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Long userId) {
        User result = userRepository.findById(userId).orElse(null);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        User shouldNotFind = userRepository.findByEmail(newUser.getEmail());
        if (shouldNotFind != null) {
            return new ResponseEntity<>(shouldNotFind, HttpStatus.CONFLICT);
        }
        newUser.setType(UserType.NORMAL);
        User persisted = userRepository.save(newUser);
        return ResponseEntity.ok(persisted);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(name = "id") Long userId, @RequestBody User toUpdate) {
        User existing = userRepository.findById(userId).orElse(null);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        existing.setEmail(toUpdate.getEmail());
        existing.setPassword(toUpdate.getPassword());
        existing.setName(toUpdate.getName());

        User persisted = userRepository.save(existing);
        return ResponseEntity.ok(persisted);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long userId) {
        User existing = userRepository.findById(userId).orElse(null);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("Deleting user:" + existing);
        userRepository.delete(existing);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @GetMapping("/login/account")
    public Principal user(Principal principal) {
        log.info("user logged " + principal);
        return principal;
    }
}
