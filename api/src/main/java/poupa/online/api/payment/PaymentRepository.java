package poupa.online.api.payment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    List<PaymentEntity> findByUserIdOrderByPayedAtAsc(UUID userId);

    @Query(value = "SELECT * FROM payments WHERE goal_id = :goalId AND status = 2 ORDER BY payed_at DESC LIMIT 50", nativeQuery = true)
    List<PaymentEntity> findLastPayedPayments(@Param("goalId") UUID goalId);

    @Query(value = "SELECT SUM(payment_value_in_cents) FROM payments WHERE goal_id = :goalId AND status = 2 GROUP BY goal_id", nativeQuery = true)
    List<Integer> getTotalOfPayments(@Param("goalId") UUID goalId);

    @Modifying
    @Query("""
                UPDATE payments SET
                    status = :status,
                    paymentGatewayIdentfier = :identfier,
                    qrCode = :qrCode,
                    qrCodeBase64 = :qrCodeBase64,
                    dateOfExpiration = :dateOfExpiration
                WHERE id = :id
            """)
    void setMakePaymentData(
            @Param("id") UUID id,
            @Param("status") PaymentStatus status,
            @Param("identfier") Long identfier,
            @Param("qrCode") String qrCode,
            @Param("qrCodeBase64") String qrCodeBase64,
            @Param("dateOfExpiration") LocalDateTime dateOfExpiration);

    @Modifying
    @Query("""
                UPDATE payments SET
                    status = :status,
                    paymentGatewayIdentfier = :identfier,
                    qrCode = :qrCode,
                    qrCodeBase64 = :qrCodeBase64,
                    dateOfExpiration = :dateOfExpiration,
                    payedAt = :payedAt
                WHERE id = :id
            """)
    void setMakePaymentData(
            @Param("id") UUID id,
            @Param("status") PaymentStatus status,
            @Param("identfier") Long identfier,
            @Param("payedAt") LocalDateTime payedAt,
            @Param("qrCode") String qrCode,
            @Param("qrCodeBase64") String qrCodeBase64,
            @Param("dateOfExpiration") LocalDateTime dateOfExpiration);

}
