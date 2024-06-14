package com.supamenu.www;

import com.supamenu.www.enumerations.user.EUserRole;
import com.supamenu.www.services.interfaces.RoleService;
import com.supamenu.www.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableCaching
@RequiredArgsConstructor
public class SpringBootMain {
    private final RoleService roleService;
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }

    @Bean
    public void registerRoles() {
        Set<EUserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(EUserRole.ADMIN);
        userRoleSet.add(EUserRole.USER);
        for (EUserRole role : userRoleSet) {
            if (!this.roleService.isRolePresent(role)) {
                this.roleService.createRole(role);
            }
        }
    }

//    @Bean
//    public void registerAdminUser() {
//        if (!this.roleService.isRolePresent(EUserRole.ADMIN)) {
//
//        }
//    }
}
