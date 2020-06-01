package pl.sgnit.ims.util;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PasswordCheckerTest {

    private PasswordChecker passwordChecker = new PasswordChecker();

    @Test
    public void checkPasswordLength() {
        String password = "1234567";

        String passwordMessage = passwordChecker.checkPassword(password);

        assertNotEquals("OK", passwordMessage);
    }

    @Test
    public void checkPasswordContainsSmallLetter() {
        String password = "1234A67!";

        String passwordMessage = passwordChecker.checkPassword(password);

        assertNotEquals("OK", passwordMessage);
    }

    @Test
    public void checkPasswordContainsCapitalLetter() {
        String password = "1234a67!";

        String passwordMessage = passwordChecker.checkPassword(password);

        assertNotEquals("OK", passwordMessage);
    }

    @Test
    public void checkPasswordContainsDigit() {
        String password = "aAsSDerv!";

        String passwordMessage = passwordChecker.checkPassword(password);

        assertNotEquals("OK", passwordMessage);
    }

    @Test
    public void checkPasswordContainsSpecialCharacter() {
        String password = "aAsSDerv1";

        String passwordMessage = passwordChecker.checkPassword(password);

        assertNotEquals("OK", passwordMessage);
    }

    @Test
    public void checkPassword() {
        String password = "aAsSD#erv1";

        String passwordMessage = passwordChecker.checkPassword(password);

        assertEquals("OK", passwordMessage);
    }
}
