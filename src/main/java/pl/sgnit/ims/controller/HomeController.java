package pl.sgnit.ims.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sgnit.ims.security.LoggedUser;
import pl.sgnit.ims.service.UserService;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false, name = "login") String login, Model model, @AuthenticationPrincipal LoggedUser loggedUser) {
        if (login == null || loggedUser == null) {
            return "index";
        }
        if (userService.hasUserStartPassword(loggedUser.getUser())) {
            model.addAttribute("user", loggedUser.getUser());
            return "user/new_password";
        }
        return "redirect:/";
    }
}
