package poupa.online.api.payment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.net.HttpStatus;

import poupa.online.api.goal.GoalEntity;
import poupa.online.api.goal.GoalRepository;
import poupa.online.api.payment.dto.MakePaymentResponseDTO;
import poupa.online.api.payment.dto.CreatePaymenteRequestDTO;
import poupa.online.api.responses.HttpResponse;
import poupa.online.api.user.UserEntity;
import poupa.online.api.user.UserRepository;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<HttpResponse<PaymentEntity>> create(@RequestBody CreatePaymenteRequestDTO paymentData) {
        var response = new HttpResponse<PaymentEntity>();

        UserEntity user = userRepository.findById(paymentData.getUserId()).orElse(null);
        GoalEntity goal = goalRepository.findById(paymentData.getGoalId()).orElse(null);

        PaymentEntity payment = new PaymentEntity();
        payment.setDescription(paymentData.getDescription());
        payment.setGoal(goal);
        payment.setUser(user);
        payment.setPaymentValueInCents(paymentData.getPaymentValueInCents());
        payment.setStatus(PaymentStatus.PENDING);

        PaymentEntity createdPayment = repository.save(payment);
        response.setMessage("success");
        response.setData(createdPayment);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/payed/{goalId}")
    public List<PaymentEntity> getAll(@PathVariable UUID goalId) {
        List<PaymentEntity> payments = repository.findLastPayedPayments(goalId);
        return payments;
    }

    @GetMapping("user/{userId}")
    public List<PaymentEntity> getByUserId(@PathVariable UUID userId) {
        System.out.println(userId + "ee");
        List<PaymentEntity> payments = repository.findByUserIdOrderByPayedAtAsc(userId);
        return payments;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse<MakePaymentResponseDTO>> makePayment(@PathVariable UUID id) {
        var response = new HttpResponse<MakePaymentResponseDTO>();
        try {

            PaymentEntity payment = repository.findById(id).orElse(null);

            if (payment == null) {
                response.setMessage("payment not found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (payment.getStatus().equals(PaymentStatus.COMPLETED)) {
                response.setMessage("payment is already completed");
                return ResponseEntity.ok().body(response);
            }

            var now = LocalDateTime.now();

            if (payment.getStatus() == PaymentStatus.PENDING || payment.getDateOfExpiration().isBefore(now)) {
                response.setData(paymentService.makePayment(payment));
                return ResponseEntity.ok().body(response);
            }

            response.setData(paymentService.getMakePaymentData(payment));

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            response.setMessage("payment gateway request failed -> " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/notification/{paymentId}")
    public ResponseEntity<String> notification(@PathVariable UUID paymentId, @RequestParam String topic,
            @RequestParam(required = false) String id) throws NumberFormatException, Exception {

        if (id == null) {
            return ResponseEntity.ok("");
        }
        paymentService.paymentNotificationAgent(topic, Long.parseLong(id), paymentId);
        return ResponseEntity.ok("");
    }

    @GetMapping("/total/{goalId}")
    ResponseEntity<HttpResponse<Integer>> getTotal(@PathVariable UUID goalId) {

        var response = new HttpResponse<Integer>();

        List<Integer> total = repository.getTotalOfPayments(goalId);
        response.setData(total.get(0));

        return ResponseEntity.ok().body(response);

    }

}
