package tacos.controllers.api_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import tacos.data.TacoRepository;
import tacos.entity.Taco;

@RestController
@RequestMapping(path="/api/tacos", produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class TacoApiController {

    private TacoRepository repo;
    
    @Autowired
    public TacoApiController(TacoRepository repo) {
        this.repo = repo;
    }
    
    @GetMapping
    public Iterable<Taco> allIngredients() {
        return repo.findAll();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Taco saveIngredient(@RequestBody Taco taco) {
        return repo.save(taco);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("id") long tacoId) {
        repo.deleteById(tacoId);
    }    
}
