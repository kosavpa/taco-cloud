package tacos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.data.UserRepository;
import tacos.security.RegistrationForm;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registrationForm(){

        return "registration";
    }

    @PostMapping
    public String processRregistration(RegistrationForm registrationForm, Model model){
        
        if(!registrationForm.getConfirmPassword().equals(registrationForm.getPassword())){
            model.addAttribute("passwordConfirmError", "Password and confirm password not identity");

            return "registration";
        }

        userRepository.save(registrationForm.toUser(passwordEncoder));

        return "redirect:/login";
    }
}