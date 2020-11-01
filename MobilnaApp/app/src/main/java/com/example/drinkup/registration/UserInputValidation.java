package com.example.drinkup.registration;

import android.util.Patterns;

public class UserInputValidation {
    
    public static boolean isNameDefined(String name) {
        if (name == null) {
            return false;
        }
        if (name.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isOibInValidFormat(String oib) {
        if (oib == null) {
            return false;
        }
        if (!oib.matches("^[0-9]{11}$")) {
            return false;
        }
        return true;
    }

    public static boolean arePasswordsSame(String password, String repeatedPassword) {
        if (password == null || repeatedPassword == null) {
            return false;
        }
        if (!password.equals(repeatedPassword)) {
            return false;
        }
        return true;
    }

    // A placeholder email validation check
    public static boolean isEmailInValidFormat(String email) {
        if (email == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // A placeholder password validation check
    public static boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        if (password.length() < 8){
            return false;
        }
        if (!password.matches("^(?=.*[A-Za-z])(?=.*[0-9]).+$")) {
            return false;
        }
        return true;
    }
}
