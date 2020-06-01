package pl.sgnit.ims.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordChecker {

    private final int MIN_PASSWORD_LENGTH = 8;

    public String checkPassword(String password) {
        if (!isRightLength(password)) {
            return String.format("Hasło musi mieć co najmniej %d znaków", MIN_PASSWORD_LENGTH);
        }
        if (!containsSmallLetter(password)) {
            return "Hasło musi zawierać co najmniej jedną małą literę";
        }
        if (!containsCapitalLetter(password)) {
            return "Hasło musi zawierać co najmniej jedną wielką literę";
        }
        if (!containsDigit(password)) {
            return "Hasło musi zawierać co najmniej jedną cyfrę";
        }
        if (!containsSpecialCharacter(password)) {
            return "Hasło musi zawierać co najmniej jeden znak specjalny";
        }
        return "OK";
    }

    private boolean isRightLength(String password) {
        return password.length()>=MIN_PASSWORD_LENGTH;
    }

    private boolean containsSmallLetter(String password) {
        boolean result = false;

        for (char c : password.toCharArray()) {
            result = result || Character.isLowerCase(c);
        }
        return result;
    }

    private boolean containsCapitalLetter(String password) {
        boolean result = false;

        for (char c : password.toCharArray()) {
            result = result || Character.isUpperCase(c);
        }
        return result;
    }

    private boolean containsDigit(String password) {
        boolean result = false;

        for (char c : password.toCharArray()) {
            result = result || Character.isDigit(c);
        }
        return result;
    }

    private boolean containsSpecialCharacter(String password) {
        String[] specialCharacters = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "-", "=", "+", "[", "]", "{", "}", "<", ">"};
        boolean result = false;

        for (String s : specialCharacters) {
            result = result || password.contains(s);
        }
        return result;
    }
}
