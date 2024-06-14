package com.supamenu.www.security;

import com.supamenu.www.enumerations.user.EUserStatus;
import com.supamenu.www.repositories.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.supamenu.www.exceptions.BadRequestAlertException;
import com.supamenu.www.models.User;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetails loadByUserId(UUID id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserByUsername(String s) throws BadRequestAlertException {
        User user = userRepository.findUserByEmailOrUsername(s, s).orElseThrow(() -> new UsernameNotFoundException("user not found with email or mobile of " + s));
        if (!user.isVerified()) {
            throw new BadRequestAlertException("User is not verified");
        }
        if (!user.getStatus().equals(EUserStatus.ACTIVE)) {
            throw new BadRequestAlertException("User is not active");
        }
        return UserPrincipal.create(user);
    }
}