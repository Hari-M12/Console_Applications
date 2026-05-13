package com.zsgs.thiranx.features.signin;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.LoginRequest;
import com.zsgs.thiranx.data.repository.ThiranXDB;
import com.zsgs.thiranx.enums.EmployeeStatus;

import java.util.List;
import java.util.regex.Pattern;

class SignInModel {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final SignInView signInView;


    public SignInModel(SignInView signInView){
        this.signInView = signInView;
    }

    void authenticate(LoginRequest loginRequest) {
        if (loginRequest == null) {
            signInView.onSignInFailed("Invalid email or password");
            return;
        }

        String emailError = validateEmail(loginRequest.getEmailId());
        if(emailError!=null){
            signInView.onSignInFailed(emailError);
            return;
        }
        String passwordError = validatePassword(loginRequest.getPassword());
        if(passwordError!=null){
            signInView.onSignInFailed(passwordError);
            return;
        }

        Employee employee = ThiranXDB.getInstance().authenticateEmployee(loginRequest.getEmailId(), loginRequest.getPassword());
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
}
