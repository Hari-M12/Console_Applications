package com.haridev.washtrack.features.employee.add;

import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.enums.Role;
import com.haridev.washtrack.util.ConsoleInput;

import java.util.Scanner;

public class EmployeeAddView {
    private final EmployeeAddModel employeeAddModel;
    private final Scanner scanner;
    private final Employee employee;

    public EmployeeAddView(Employee employee) {
        this.employeeAddModel = new EmployeeAddModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }

    public void init() {
        System.out.println();
        System.out.println("Add employee");

        boolean firstEmployee = employeeAddModel.isFirstEmployee();

        String name = promptName();
        String email = promptEmail();
        String password = promptPassword();
        String mobile = promptMobile();
        Long dob = promptDob();
        String address = promptAddress();
        Double salary = employeeAddModel.calculateSalary(employee);
        Role role;
        if (firstEmployee) {
            System.out.println("As the first user in the system, you will be registered as a Owner.");
            role = Role.OWNER;
        } else {
            role = promptRole();
        }

        employeeAddModel.registerEmployee(name, email, password, mobile, dob, role, address,salary);
    }

    private String promptName() {
        while (true) {
            System.out.print("Enter your full name: ");
            String input = scanner.nextLine();
            String error = employeeAddModel.validateName(input);
            if (error == null)
                return input.trim();
            showErrorMessage(error);
        }
    }

    private String promptEmail() {
        while (true) {
            System.out.print("Enter your email: ");
            String input = scanner.nextLine();
            String error = employeeAddModel.validateEmail(input);
            if (error == null)
                return input.trim();
            showErrorMessage(error);
        }
    }

    private String promptPassword() {
        while (true) {
            System.out.print("Enter password (minimum 8 characters with letters and numbers): ");
            String input = scanner.nextLine();
            String error = employeeAddModel.validatePassword(input);
            if (error != null) {
                showErrorMessage(error);
                continue;
            }
            System.out.print("Confirm password: ");
            String confirm = scanner.nextLine();
            String confirmError = employeeAddModel.validateConfirmPassword(input, confirm);
            if (confirmError != null) {
                showErrorMessage(confirmError);
                continue;
            }
            return input;
        }
    }

    private String promptAddress() {
        while (true) {
            System.out.print("Enter your address: ");
            String input = scanner.nextLine();
            String error = employeeAddModel.validateAddress(input);
            if (error == null)
                return input.trim();
            showErrorMessage(error);
        }
    }

    private String promptMobile() {
        while (true) {
            System.out.print("Enter your 10 digit mobile number: ");
            String input = scanner.nextLine();
            String error = employeeAddModel.validateMobile(input);
            if (error == null)
                return input.trim();
            showErrorMessage(error);
        }
    }

    private Long promptDob() {
        while (true) {
            System.out.print("Enter your date of birth (dd-MM-yyyy): ");
            String input = scanner.nextLine();
            Long dob = employeeAddModel.parseDateOfBirth(input);
            if (dob != null)
                return dob;
            showErrorMessage("Enter a valid date. You must be at least 18 years old.");
        }
    }

    private Role promptRole() {
        while (true) {
            System.out.println("Select your role:");
            System.out.println("1. Manager");
            System.out.println("2. Employee");
            System.out.print("Choose an option: ");
            String input = scanner.nextLine();
            Role role = employeeAddModel.parseRole(input);
            if (role != null)
                return role;
            showErrorMessage("Select a valid role.");
        }
    }

    void onEmployeeAdded(Employee employee) {
        System.out.println();
        System.out.println("Employee added successfully.");
        System.out.println("Employee id: " + employee.getId());
    }

    void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

}
