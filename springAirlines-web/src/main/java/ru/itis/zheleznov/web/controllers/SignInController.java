package ru.itis.zheleznov.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignInController {

    @GetMapping("/signIn")
    public String getSignInPage(@RequestParam(required=false) String error, Model model) {
        model.addAttribute("signIn", "");

        if (error != null) {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
        }
        return "sign_in";
    }
}
