package com.supamenu.www.services.implementations;

import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.dtos.role.CreateRoleDTO;
import com.supamenu.www.enumerations.user.EUserRole;
import com.supamenu.www.exceptions.*;
import com.supamenu.www.models.Role;
import com.supamenu.www.repositories.IRoleRepository;
import com.supamenu.www.services.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final IRoleRepository roleRepository;

    @Override
    @Transactional
    public void createRole(EUserRole roleName) {
        Optional<Role> optionalRole = roleRepository.findRoleByName(roleName);
        if (optionalRole.isPresent()) {
            throw new BadRequestAlertException("The role already exists");
        } else {
            Role role = new Role(roleName);
            try {
                roleRepository.save(role);
            } catch (Exception e) {
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<Role>> createRole(CreateRoleDTO createRoleDTO) {
        try {

            Optional<Role> optionalRole = roleRepository.findRoleByName(createRoleDTO.getName());
            if (optionalRole.isPresent()) {
                throw new DuplicateRecordException("The role already exists");
            } else {
                Role role = new Role(createRoleDTO.getName());
                roleRepository.save(role);
                return ApiResponse.success("Role created successfully", HttpStatus.CREATED, role);
            }
        } catch (Exception exception) {
            throw new CustomException(exception);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles() {
        try {
            List<Role> roles = roleRepository.findAll();
            return ApiResponse.success("Roles fetched successfully", HttpStatus.OK, roles);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public Role getRoleById(UUID roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("The Role was not found"));
    }

    @Override
    public Role getRoleByName(EUserRole roleName) {
        return roleRepository.findRoleByName(roleName).orElseThrow(() -> new NotFoundException("The Role was not found"));
    }

    @Override
    public Role deleteRole(UUID roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("The role is not present"));
        try {
            roleRepository.deleteById(roleId);
            return role;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public boolean isRolePresent(EUserRole roleName) {
        try {
            return roleRepository.findRoleByName(roleName).isPresent();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
