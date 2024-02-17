package poupa.online.api.payment;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import poupa.online.api.goal.GoalEntity;
import poupa.online.api.user.UserEntity;

@Data
@Entity(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer paymentValueInCents;

    private LocalDateTime payedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "goalId")
    private GoalEntity goal;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus status;

    private Long paymentGatewayIdentfier;
    private String qrCode;
    @Column(length = 10000)
    private String qrCodeBase64;
    private LocalDateTime dateOfExpiration;

}
