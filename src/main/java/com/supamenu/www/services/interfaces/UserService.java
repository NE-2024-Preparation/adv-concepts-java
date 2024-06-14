package com.supamenu.www.services.interfaces;

import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.dtos.user.CreateUserDTO;
import com.supamenu.www.dtos.user.UpdateUserDTO;
import com.supamenu.www.dtos.user.UserRoleModificationDTO;
import com.supamenu.www.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public User createUserEntity(CreateUserDTO createUserDTO);

    public User findUserById(UUID userId);

    public User getLoggedInUser();

    public ResponseEntity<ApiResponse<User>> createUser(CreateUserDTO createUserDTO);

    public ResponseEntity<ApiResponse<List<User>>> getAllUsers();

    public ResponseEntity<ApiResponse<User>> getUserById(UUID uuid);

    public ResponseEntity<ApiResponse<User>> updateUser(UUID userId, UpdateUserDTO updateUserDTO);

    public ResponseEntity<ApiResponse<Object>> deleteUser(UUID userId);

    public ResponseEntity<ApiResponse<User>> addRoles(UUID userId, UserRoleModificationDTO userRoleModificationDTO);

    public ResponseEntity<ApiResponse<User>> removeRoles(UUID userId, UserRoleModificationDTO userRoleModificationDTO);
}
