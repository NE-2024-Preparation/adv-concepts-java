package com.supamenu.www.controllers;

import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.dtos.role.CreateRoleDTO;
import com.supamenu.www.models.Role;
import com.supamenu.www.services.implementations.RoleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceImpl roleService;

    @PostMapping("/create-role")
    public ResponseEntity<ApiResponse<Role>> createRole(@Valid @RequestBody CreateRoleDTO createRoleDTO) {
        return roleService.createRole(createRoleDTO);
    }

    @GetMapping("/get-roles")
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles() {
        return roleService.getAllRoles();
    }
}
