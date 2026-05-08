package com.haridev.washtrack.features.customer.add;

import com.haridev.washtrack.data.dto.Customer;
import com.haridev.washtrack.util.ConsoleInput;

import java.util.Scanner;

public class CustomerAddView {
    private final CustomerAddModel customerAddModel;
    private final Scanner scanner;

    public CustomerAddView() {
        this.customerAddModel = new CustomerAddModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        System.out.println();
        System.out.println("Add Customer");

        String name = promptName();
        String mobile = promptMobile();
        Long dob = promptDob();
        String address = promptAddress();
        customerAddModel.registerCustomer(name, mobile, dob, address);

        System.out.println("Enter to return");
        scanner.nextLine();
    }

    private String promptName() {
        while (true) {
            System.out.print("Enter your full name: ");
            String input = scanner.nextLine();
            String error = customerAddModel.validateName(input);
            if (error == null)
                return input.trim();
            showErrorMessage(error);
        }
    }

    private String promptAddress() {
        while (true) {
            System.out.print("Enter your address: ");
            String input = scanner.nextLine();
            String error = customerAddModel.validateAddress(input);
            if (error == null)
                return input.trim();
            showErrorMessage(error);
        }
    }

    private String promptMobile() {
        while (true) {
            System.out.print("Enter your 10 digit mobile number: ");
            String input = scanner.nextLine();
            String error = customerAddModel.validateMobile(input);
            if (error == null)
                return input.trim();
            showErrorMessage(error);
        }
    }

    private Long promptDob() {
        while (true) {
            System.out.print("Enter your date of birth (dd-MM-yyyy): ");
            String input = scanner.nextLine();
            Long dob = customerAddModel.parseDateOfBirth(input);
            if (dob != null)
                return dob;
            showErrorMessage("Enter a valid date. You must be at least 18 years old.");
        }
    }

    void onCustomerAdded(Customer customer) {
        System.out.println();
        System.out.println("Customer added successfully.");
        System.out.println("Customer id: " + customer.getId());
    }

    void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

}
