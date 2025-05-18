package com.example.springBlockchain.controller;

import com.example.springBlockchain.model.User;
import com.example.springBlockchain.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @RequestMapping("/register")
    public String index(Model model) {
        model.addAttribute("authorization", new LoginForm());
        return "register";
    }

    private final UserRepository userrepository;

    public UserController(UserRepository userrepository) {
        this.userrepository = userrepository;
    }


    @RequestMapping("/signIn")
    public String SignInController(@ModelAttribute LoginForm form) {
        User newUser = new User();
        newUser.setUsername(form.getUsername());
        newUser.setPassword(form.getPassword());
        userrepository.save(newUser);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String LoginController() {
        return "redirect:/";
    }




    public class LoginForm {
        private String username;
        private String password;

        // Обязательно нужны публичные геттеры и сеттеры
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}

