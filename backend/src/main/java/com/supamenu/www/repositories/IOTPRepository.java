package com.supamenu.www.repositories;

import com.supamenu.www.enumerations.otp.EOTPType;
import com.supamenu.www.models.OTP;
import com.supamenu.www.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOTPRepository extends JpaRepository<OTP, UUID> {
    Optional<OTP> findOTPByCodeAndUserAndType(String code, User user, EOTPType otpType);
}
