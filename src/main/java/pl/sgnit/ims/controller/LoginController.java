package pl.sgnit.ims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Błędna nazwa użytkownika lub hasło");
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @RequestMapping("/loginError")
    @ResponseBody
    public String loginError() {
        return "Nieprawidłowa nazwa użytkownika lub hasło";
    }

    @RequestMapping("/loginSuccess")
    @ResponseBody
    public String loginSuccess() {
        return "OK";
    }
}
