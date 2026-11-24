package org.springtech.lab24november.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springtech.lab24november.Exceptions.DuplicateResourceException;
import org.springtech.lab24november.Exceptions.ResourceNotFoundException;
import org.springtech.lab24november.dto.UserRequest;
import org.springtech.lab24november.dto.UserResponse;
import org.springtech.lab24november.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UsersController {
    private final UsersService service;

    public UsersController(UsersService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(){
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserRequest userInfo) throws DuplicateResourceException {
        service.createUser(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable long id, @Valid @RequestBody UserRequest updatedUserInfo) throws ResourceNotFoundException {
        service.updateUser(id, updatedUserInfo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) throws ResourceNotFoundException {
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
