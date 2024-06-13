package com.supamenu.www.models;

import com.supamenu.www.enumerations.otp.EOTPStatus;
import com.supamenu.www.enumerations.otp.EOTPType;
import com.supamenu.www.utils.Utility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "otps")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTP extends Base {
    @Column(name = "code")
    private String code = String.valueOf(Utility.generateOTP());

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EOTPType type;

    @Column(name = "duration")
    private int duration;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EOTPStatus status = EOTPStatus.UNUSED;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public OTP(EOTPType type, User user, int duration) {
        this.type = type;
        this.user = user;
        this.duration = duration;
    }

    public boolean isValid() {
        if (this.status != EOTPStatus.UNUSED) {
            return false;
        }
        LocalDateTime expiryTime = this.getCreatedAt().plusMinutes(duration);
        return LocalDateTime.now().isBefore(expiryTime);
    }
}
