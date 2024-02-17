package poupa.online.api.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poupa.online.api.responses.HttpResponse;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository repository;

    @PostMapping
    public ResponseEntity<HttpResponse<UserEntity>> create(@RequestBody UserEntity userData) {
        var response = new HttpResponse<UserEntity>();

        UserEntity searchedUser = repository.findFirstByCpf(userData.getCpf());
        if (searchedUser != null) {
            response.setMessage("this CPF is already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        UserEntity user = repository.save(userData);
        response.setMessage("user created successfully");
        response.setData(user);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public List<UserEntity> getAll() {
        List<UserEntity> users = repository.findAll();
        return users;
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<HttpResponse<UserEntity>> getByCpf(@PathVariable String cpf) {
        var response = new HttpResponse<UserEntity>();
        UserEntity user = repository.findFirstByCpf(cpf);

        if (user == null) {
            response.setMessage("not found user CPF");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.setData(user);

        return ResponseEntity.ok().body(response);

    }

}