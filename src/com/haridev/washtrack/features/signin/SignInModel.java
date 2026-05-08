package com.haridev.washtrack.features.signin;

import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.data.dto.LoginRequest;
import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.enums.EmployeeStatus;

import java.util.regex.Pattern;

class SignInModel {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final SignInView signInView;

    SignInModel(SignInView signInView) {
        this.signInView = signInView;
    }

    String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email cannot be empty";
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return "Enter a valid email address";
        }
        return null;
    }

    String validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return "Password cannot be empty";
        }
        return null;
    }

    void authenticate(LoginRequest request) {
        if (request == null) {
            signInView.onSignInFailed("Invalid email or password");
            return;
        }
        String emailError = validateEmail(request.getEmailId());
        if (emailError != null) {
            signInView.onSignInFailed(emailError);
            return;
        }
        String passwordError = validatePassword(request.getPassword());
        if (passwordError != null) {
            signInView.onSignInFailed(passwordError);
            return;
        }

        Employee employee = WashTrackDB.getInstance().authenticateEmployee(
                request.getEmailId(), request.getPassword());
        if (employee == null) {
            signInView.onSignInFailed("Invalid email or password");
            return;
        }
        if (employee.getStatus() == EmployeeStatus.INACTIVE) {
            signInView.onSignInFailed("Your account is not active. Contact your administrator.");
            return;
        }
        signInView.onSignInSuccessful(employee);
    }
}
