package com.haridev.washtrack;

import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.features.signin.SignInView;
import com.haridev.washtrack.features.signup.SignUpView;
import com.haridev.washtrack.util.ConsoleInput;

import java.util.Scanner;

public class WashTrackApplication {
    public static final int VERSION_NO = 2;
    public static final String VERSION_NAME = "1.0.0";

    public static void main(String[] args) {
        System.out.println("Welcome to WashTrack");
        System.out.println("Version " + VERSION_NAME);
        showLandingMenu();
    }

    private static void showLandingMenu() {
        Scanner scanner = ConsoleInput.getScanner();
        while (true) {

            boolean firstTime = WashTrackDB.getInstance().getAllEmployees().isEmpty();
            System.out.println();
            if (firstTime) {
                System.out.println("1. Setup Owner Account");
                System.out.println("2. Exit");
            } else {
                System.out.println("1. Sign In");
                System.out.println("2. Exit");
            }
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    if(firstTime){
                        new SignUpView().init();
                        break;
                    }else{
                        new SignInView().init();
                        break;
                    }

                case "2":
                    System.out.println("Thank you for using WashTrack");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}