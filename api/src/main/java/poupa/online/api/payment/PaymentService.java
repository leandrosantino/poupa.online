package poupa.online.api.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.payment.Payment;

import jakarta.transaction.Transactional;
import poupa.online.api.config.ApiConfig;
import poupa.online.api.payment.dto.MakePaymentResponseDTO;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    int CENTS_MULTIPLE = 100;

    MakePaymentResponseDTO makePayment(PaymentEntity payment) throws Exception {

        var client = new PaymentClient();

        var apiConfig = new ApiConfig();
        String applicationUrl = apiConfig.getApplicationUrl();
        applicationUrl = applicationUrl == "" ? null : applicationUrl + "/" + payment.getId();

        var amount = new BigDecimal(payment.getPaymentValueInCents()).divide(new BigDecimal(CENTS_MULTIPLE));
        System.out.println("Valor da transação:" + amount);

        PaymentCreateRequest createRequest = PaymentCreateRequest
                .builder()
                .transactionAmount(amount)
                .paymentMethodId("pix")
                .description(payment.getDescription())
                .notificationUrl(applicationUrl)
                .dateOfExpiration(OffsetDateTime.now().plusHours(1).plusMinutes(10))
                .payer(PaymentPayerRequest.builder()
                        .email("payer@gmail.com")
                        .firstName(payment.getUser().getName())
                        .identification(IdentificationRequest.builder()
                                .type("CPF")
                                .number(payment.getUser().getCpf())
                                .build())
                        .build())
                .build();

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", payment.getId().toString());

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();

        Payment paymentData = null;
        try {
            paymentData = client.create(createRequest, requestOptions);
        } catch (MPApiException ex) {
            String errorMessage = String.format(
                    "MercadoPago Error. Status: %s, Content: %s%n",
                    ex.getApiResponse().getStatusCode(),
                    ex.getApiResponse().getContent());

            System.out.printf(errorMessage);
            throw new Exception(errorMessage);
        }

        var makePaymentResponse = new MakePaymentResponseDTO();

        makePaymentResponse.setId(paymentData.getId());
        makePaymentResponse.setQrCode(paymentData.getPointOfInteraction().getTransactionData().getQrCode());
        makePaymentResponse.setQrCodeBase64(
                paymentData.getPointOfInteraction().getTransactionData().getQrCodeBase64());
        makePaymentResponse.setDescription(paymentData.getDescription());

        System.out.println(paymentData.getDateOfExpiration());
        System.out.println(paymentData.getDateOfExpiration().toLocalDateTime());

        makePaymentResponse.setDateOfExpiration(paymentData.getDateOfExpiration().toLocalDateTime());
        makePaymentResponse.setStatus(PaymentStatus.PROCESSING);

        System.out.println("Qrcode bse64 length: " + makePaymentResponse.getQrCodeBase64().length());

        this.paymentRepository.setMakePaymentData(
                payment.getId(),
                PaymentStatus.PROCESSING,
                makePaymentResponse.getId(),
                makePaymentResponse.getQrCode(),
                makePaymentResponse.getQrCodeBase64(),
                makePaymentResponse.getDateOfExpiration());

        return makePaymentResponse;
    }

    MakePaymentResponseDTO getMakePaymentData(PaymentEntity payment) {
        var makePaymentResponse = new MakePaymentResponseDTO();

        makePaymentResponse.setId(payment.getPaymentGatewayIdentfier());
        makePaymentResponse.setDescription(payment.getDescription());
        makePaymentResponse.setStatus(payment.getStatus());
        makePaymentResponse.setDateOfExpiration(payment.getDateOfExpiration());
        makePaymentResponse.setQrCode(payment.getQrCode());
        makePaymentResponse.setQrCodeBase64(payment.getQrCodeBase64());

        return makePaymentResponse;
    }

    @Async
    void paymentNotificationAgent(String topic, Long paymentGatewayIdentfier, UUID paymentId) throws Exception {

        if (topic.equals("payment")) {
            var paymentClient = new PaymentClient();
            try {
                Payment payment = paymentClient.get(paymentGatewayIdentfier);

                PaymentEntity paymentData = paymentRepository.findById(paymentId).orElse(null);

                if (paymentData == null)
                    return;

                if (payment.getStatus().equals("approved")
                        && !paymentData.getStatus().equals(PaymentStatus.COMPLETED)) {
                    paymentRepository.setMakePaymentData(
                            paymentId,
                            PaymentStatus.COMPLETED,
                            paymentGatewayIdentfier,
                            LocalDateTime.now(),
                            null, null, null);
                    System.out.println("Completed payment");
                }

            } catch (MPApiException ex) {
                String errorMessage = String.format(
                        "MercadoPago Error. Status: %s, Content: %s%n",
                        ex.getApiResponse().getStatusCode(),
                        ex.getApiResponse().getContent());

                System.out.printf(errorMessage);
                throw new Exception(errorMessage);
            }
        }

    }

}
