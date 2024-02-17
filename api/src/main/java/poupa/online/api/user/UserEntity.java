package poupa.online.api.user;

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
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<PaymentEntity> payments;
}
