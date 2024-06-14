package com.supamenu.www.services.interfaces;

import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.dtos.role.CreateRoleDTO;
import com.supamenu.www.enumerations.user.EUserRole;
import com.supamenu.www.models.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles();

    public Role getRoleById(UUID roleId);

    public Role getRoleByName(EUserRole roleName);

    public void createRole(EUserRole roleName);

    public ResponseEntity<ApiResponse<Role>> createRole(CreateRoleDTO createRoleDTO);

    public Role deleteRole(UUID roleId);

    public boolean isRolePresent(EUserRole roleName);
}
