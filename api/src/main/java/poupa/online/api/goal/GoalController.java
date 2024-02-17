package poupa.online.api.goal;

import java.util.List;
import java.util.UUID;

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

@RestController
@RequestMapping("/goal")
public class GoalController {

    @Autowired
    private GoalRepository repository;

    @PostMapping
    public ResponseEntity<HttpResponse<GoalEntity>> create(@RequestBody GoalEntity goalData) {
        var response = new HttpResponse<GoalEntity>();
        response.setMessage("Success!");
        var goal = repository.save(goalData);
        response.setData(goal);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    private List<GoalEntity> getAll() {
        List<GoalEntity> goals = this.repository.findAll();
        return goals;
    }

    @GetMapping("/{id}")
    private ResponseEntity<HttpResponse<GoalEntity>> getById(@PathVariable UUID id) {
        GoalEntity searchGoal = repository.findById(id).orElse(null);
        var reponse = new HttpResponse<GoalEntity>();
        reponse.setData(searchGoal);

        if (searchGoal == null) {
            reponse.setMessage("not found goal id");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reponse);
        }

        return ResponseEntity.ok().body(reponse);
    }

}
