package pl.sgnit.ims.controller;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.ui.*;
import pl.sgnit.ims.model.*;
import pl.sgnit.ims.security.*;
import pl.sgnit.ims.service.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    private static HomeController homeController;
    private static UserService userService;

    @BeforeAll
    public static void before() {
        userService = Mockito.mock(UserService.class);
        homeController = new HomeController(userService);
    }

    @Test
    void homeWithNotUserLogged() {
        Model model = new ExtendedModelMap();
        String login = null;
        LoggedUser loggedUser = null;

        String returnValue = homeController.home(login, model, loggedUser);
        Assertions.assertEquals("index", returnValue);
    }

    @Test
    void homeLoggedUserWithStartPassword() {
        Model model = new ExtendedModelMap();
        String login = "user";
        User user = new User();
        LoggedUser loggedUser = new LoggedUser("user", "passowrd", new HashSet<>(), user);

        Mockito.when(userService.hasUserStartPassword(user)).thenReturn(true);
        String returnValue = homeController.home(login, model, loggedUser);
        Assertions.assertEquals("user/new_password", returnValue);
        Assertions.assertTrue(model.containsAttribute("user"));
    }

    @Test
    void homeLoggedUserWithOwnPassword() {
        Model model = new ExtendedModelMap();
        String login = "user";
        User user = new User();
        LoggedUser loggedUser = new LoggedUser("user", "passowrd", new HashSet<>(), user);

        Mockito.when(userService.hasUserStartPassword(user)).thenReturn(false);
        String returnValue = homeController.home(login, model, loggedUser);
        Assertions.assertEquals("redirect:/", returnValue);
    }
}
