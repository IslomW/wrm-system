package com.sharom.wrm.modules.user.controller;

import com.sharom.wrm.common.response.ApiResponse;
import com.sharom.wrm.common.response.ResponseFactory;
import com.sharom.wrm.modules.user.model.dto.UserDTO;
import com.sharom.wrm.modules.user.service.UserService;
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


    @GetMapping("/whoami")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser(){
        return ResponseFactory.ok(userService.getCurrentUser());
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> create(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.create(userDTO);
        return ResponseFactory.created(createdUser);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getById(@PathVariable String id) {
        UserDTO user = userService.getById(id);
        return ResponseFactory.ok(user);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> update(
            @PathVariable String id,
            @RequestBody UserDTO userDTO
    ) {
        UserDTO updatedUser = userService.update(id, userDTO);
        return ResponseFactory.ok(updatedUser);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/location")
    public ResponseEntity<ApiResponse<UserDTO>> setUserLocation(@RequestParam String id,
                                                   @RequestParam String locationId) {
        return ResponseFactory.ok(userService.setUserLocation(id, locationId));
    }
}
