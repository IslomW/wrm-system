package com.sharom.wrm.controller;

import com.sharom.wrm.payload.UserDTO;
import com.sharom.wrm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    // CREATE
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.create(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable String id) {
        UserDTO user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable String id,
            @RequestBody UserDTO userDTO
    ) {
        UserDTO updatedUser = userService.update(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/location")
    public ResponseEntity<UserDTO> setUserLocation(@RequestParam String id,
                                                   @RequestParam String locationId) {
        return ResponseEntity.ok(userService.setUserLocation(id, locationId));
    }
}
