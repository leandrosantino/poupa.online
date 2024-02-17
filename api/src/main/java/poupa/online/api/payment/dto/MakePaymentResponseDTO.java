package poupa.online.api.payment.dto;

import java.time.LocalDateTime;

import lombok.Data;
import poupa.online.api.payment.PaymentStatus;

@Data
public class MakePaymentResponseDTO {
    private Long id;
    private String qrCode;
    private String qrCodeBase64;
    private PaymentStatus status;
    private String description;
    private LocalDateTime dateOfExpiration;
}
