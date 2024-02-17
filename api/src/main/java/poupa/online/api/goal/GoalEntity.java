package poupa.online.api.goal;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import poupa.online.api.payment.PaymentEntity;

@Data
@Entity(name = "goals")
public class GoalEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer targetValueInCents;

    @JsonIgnore
    @OneToMany(mappedBy = "goal")
    private List<PaymentEntity> payments;
}
