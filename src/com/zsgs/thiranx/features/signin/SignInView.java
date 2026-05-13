package com.zsgs.thiranx.features.signin;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.LoginRequest;
import com.zsgs.thiranx.features.home.HomeView;
import com.zsgs.thiranx.util.ConsoleInput;

import java.util.Scanner;

public class SignInView {
    private final SignInModel signInModel;
    private final Scanner scanner;
    private boolean authenticated;


    public SignInView(){
        this.signInModel = new SignInModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.authenticated = false;
    }

    public void init(){
        startSignIn();
    }

    private void startSignIn() {
        System.out.println();
        System.out.println("SignIn To ThiranX");

        while (!authenticated){
            promptAndAuthenticate();
            if (authenticated) return;
        }
    }

    private void promptAndAuthenticate() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmailId(email == null ? null : email.trim());
        loginRequest.setPassword(password);

        signInModel.authenticate(loginRequest);

    }

    void onSignInFailed(String message) {
        System.out.println(message);
    }

    public void onSignInSuccessful(Employee employee) {
        authenticated = true;
        System.out.println("Welcome, " + employee.getName());
        new HomeView(employee).init();
    }
}
