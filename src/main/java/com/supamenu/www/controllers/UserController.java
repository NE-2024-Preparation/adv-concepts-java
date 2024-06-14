package com.supamenu.www.controllers;

import com.supamenu.www.dtos.user.CreateUserDTO;
import com.supamenu.www.dtos.user.UpdateUserDTO;
import com.supamenu.www.services.interfaces.UserService;
import com.supamenu.www.dtos.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.supamenu.www.models.User;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController {
    private final UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody CreateUserDTO createUserDTO) {
        return this.userService.createUser(createUserDTO);
    }

    @GetMapping("/get-users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    @PatchMapping("/update-user/{userId}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable UUID userId, @RequestBody UpdateUserDTO updateUserDTO) {
        return userService.updateUser(userId, updateUserDTO);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable UUID userId) {
        return userService.deleteUser(userId);
    }
}
