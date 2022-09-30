package tacos.controllers.api_controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.data.UserRepository;
import tacos.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(path="/api/users", produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class UserApiController {
    
    private UserRepository repo;
    
    @Autowired
    public UserApiController(UserRepository repo) {
        this.repo = repo;
    }
    
    @GetMapping
    public Iterable<User> allIngredients() {
        return repo.findAll();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveIngredient(@RequestBody User user) {
        return repo.save(user);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("id") long userId) {
        repo.deleteById(userId);
    }  
}
