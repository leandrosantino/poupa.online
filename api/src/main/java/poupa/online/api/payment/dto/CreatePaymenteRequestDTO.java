package poupa.online.api.payment.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CreatePaymenteRequestDTO {
    private String description;
    private Integer paymentValueInCents;
    private UUID userId;
    private UUID goalId;
}
