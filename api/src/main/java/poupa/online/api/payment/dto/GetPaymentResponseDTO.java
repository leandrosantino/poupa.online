package poupa.online.api.payment.dto;

import java.util.UUID;

import lombok.Data;
import poupa.online.api.payment.PaymentStatus;

@Data
public class GetPaymentResponseDTO {
    private UUID id;
    private String description;
    private Integer paymentValueInCents;
    private PaymentStatus status;
}
