package com.zsgs.thiranx;

import com.zsgs.thiranx.features.signin.SignInView;
import com.zsgs.thiranx.features.signup.SignUpView;
import com.zsgs.thiranx.util.ConsoleInput;

import java.util.Scanner;

public class ThiranXApplicationMain {
    public static final int VERSION_NO = 2;
    public static final String VERSION_NAME = "1.0.0";

    public static void main(String[] args) {
        System.out.println("Welcome to ThiranX");
        System.out.println("Version "+VERSION_NAME);
        landingMenu();
    }

    private static void landingMenu(){
        Scanner instance = ConsoleInput.getScanner();
        while (true){
            System.out.println("1.SignUp");
            System.out.println("2.SignIn");
            System.out.println("3.Exit");
            System.out.println("Choose your options");

            String choice = instance.nextLine().trim();

            switch (choice){
                case "1" :{
                    new SignUpView().init();
                    break;
                }
                case "2" :{
                    new SignInView().init();
                    break;
                }
                case "3" :{
                    System.out.println("Thank u for using ThiranX");
                    return;
                }
                default:
                    System.out.println("Invalid option.Please Try Again");
            }
        }
    }
}
