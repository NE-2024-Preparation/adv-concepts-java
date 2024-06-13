package com.supamenu.www.services.implementations;

import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.dtos.user.CreateUserDTO;
import com.supamenu.www.dtos.user.UpdateUserDTO;
import com.supamenu.www.exceptions.*;
import com.supamenu.www.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.supamenu.www.enumerations.user.EUserStatus;
import com.supamenu.www.enumerations.user.EUserRole;
import com.supamenu.www.models.Role;
import com.supamenu.www.models.User;
import com.supamenu.www.repositories.IUserRepository;
import com.supamenu.www.services.interfaces.MailService;
import com.supamenu.www.utils.HashUtil;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private IUserRepository userRepository;
    private RoleServiceImpl roleService;

    private final MailService mailService;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository, RoleServiceImpl roleService, MailServiceImpl mailService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<User>> createUser(CreateUserDTO createUserDTO) {
        try {
            Optional<User> foundUser = userRepository.findUserByEmailOrUsername(createUserDTO.getEmail(), createUserDTO.getUsername());
            if (foundUser.isPresent())
                throw new DuplicateRecordException("The user with the given email or username already exists");
            User user = new User();
            Role role = roleService.getRoleByName(EUserRole.USER);
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setUsername(createUserDTO.getUsername());
            user.setFirstName(createUserDTO.getFirstName());
            user.setLastName(createUserDTO.getLastName());
            user.setStatus(EUserStatus.ACTIVE);
            user.setEmail(createUserDTO.getEmail());
            user.setUsername(createUserDTO.getUsername());
            user.setPassword(HashUtil.hashPassword(createUserDTO.getPassword()));
            user.setFullName(createUserDTO.getFirstName() + " " + createUserDTO.getLastName());
            user.setRoles(roles);
            userRepository.save(user);
            return ApiResponse.success(
                    "Successfully created user",
                    HttpStatus.CREATED,
                    user
            );
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                user.setFullName(user.getFirstName() + " " + user.getLastName());
            }
            return ApiResponse.success(
                    "Successfully fetched all users",
                    HttpStatus.OK,
                    users
            );
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<User>> getUserById(UUID uuid) {
        try {
            User user = findUserById(uuid);
            return ApiResponse.success(
                    "Successfully fetched user",
                    HttpStatus.OK,
                    user
            );
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("The Resource was not found"));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<User>> updateUser(UUID userId, UpdateUserDTO updateUserDTO) {
        try {
            User user = findUserById(userId);
            user.setEmail(updateUserDTO.getEmail());
            user.setUsername(updateUserDTO.getUsername());
            return ApiResponse.success(
                    "Successfully updated the user",
                    HttpStatus.OK,
                    user
            );
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<Object>> deleteUser(UUID userId) {
        try {
            User user = findUserById(userId);
            userRepository.deleteById(userId);
            return ApiResponse.success(
                    "Successfully deleted the user",
                    HttpStatus.OK,
                    null
            );
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
