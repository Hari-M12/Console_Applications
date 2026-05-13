package com.zsgs.thiranx.features.employee.add;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.repository.ThiranXDB;
import com.zsgs.thiranx.enums.Role;
import com.zsgs.thiranx.features.employee.list.EmployeeListModel;
import com.zsgs.thiranx.util.ConsoleInput;
import com.zsgs.thiranx.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class EmployeeAddView {

    private final EmployeeAddModel employeeAddModel;
    private final Scanner scanner;
    private final Employee employee;

    public EmployeeAddView(Employee employee){
        this.employeeAddModel = new EmployeeAddModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }



    public void init() {
        System.out.println();
        System.out.println("Add an employee");
        boolean firstEmployee = employeeAddModel.isFirstEmployee();
        String name = promptName();
        String email = promptEmail();
        String password = promptPassword();
        String mobile = promptMobile();
        Long dob = promptDob();
        Role role;
        Long reportingTo;
        if(firstEmployee){
            System.out.println("As the first user in the system, you will be registered as a Manager.");
            role = Role.MANAGER;
            reportingTo = null;
        }else{
            role = promptRole();
            reportingTo = role == Role.EMPLOYEE ? promptReportingManager() : null;
        }

        employeeAddModel.saveEmployee(name, email, password, mobile, dob, role, reportingTo);
    }




    private String promptName() {
        while (true){
            System.out.println("Enter name ");
            String input = scanner.nextLine();
            String error = employeeAddModel.validateName(input);
            if(error==null) return input.trim();
            showErrorMessage(error);
        }
    }

    private String promptEmail() {
        while(true){
            System.out.println("Enter email");
            String input = scanner.nextLine();
            String error = employeeAddModel.validateEmail(input);
            if(error==null) return input.trim();
            showErrorMessage(error);
        }
    }

    private String promptPassword(){
        while(true){
            System.out.println("Enter password");
            String password = scanner.nextLine();
            String error = employeeAddModel.validatePassword(password);
            if(error!=null) {
                showErrorMessage(error);
                continue;
            }
            System.out.println("Confirm Password");
            String confirmPassword = scanner.nextLine();
            String confirmError = employeeAddModel.validateConfirmPassword(password,confirmPassword);
            if(confirmError != null) {
                showErrorMessage(confirmError);
                continue;
            }
            return password;
        }
    }

    private Long promptDob(){
        while (true){
            System.out.println("enter dob in dd-MM-yyyy");
            String input = scanner.nextLine();
            Long dob = employeeAddModel.validateDob(input);
            if(dob!=null) return dob;
            showErrorMessage("Enter a valid date. You must be at least 18 years old.");
        }
    }

    private String promptMobile() {
        while (true) {
            System.out.print("Enter your 10 digit mobile number: ");
            String input = scanner.nextLine();
            String error = employeeAddModel.validateMobile(input);
            if (error == null) return input.trim();
            showErrorMessage(error);
        }
    }

    private Role promptRole() {
        while (true) {
            System.out.println("Select your role:");
            System.out.println("1. Manager");
            System.out.println("2. Employee");
            System.out.print("Choose an option: ");
            String input = scanner.nextLine();
            Role role = EmployeeAddModel.parseRole(input);
            if(role!=null)return role;
            showErrorMessage("Select a valid role");
        }
    }

    private Long promptReportingManager() {
        List<Employee> managers = ThiranXDB.getInstance().getActiveManagers();
        if (managers.isEmpty()) {
            System.out.println("No manager is available yet. You can update your reporting manager later.");
            return null;
        }
        while(true){
            System.out.println("Select your reporting manager:");
            for (int i = 0; i < managers.size(); i++) {
                Employee manager = managers.get(i);
                System.out.println((i + 1) + ". " + manager.getName() + " (" + manager.getEmployeeId() + ")");
            }
            System.out.print("Choose an option: ");
            Integer index = ParseHelper.parseNonNegativeInt(scanner.nextLine());
            if(index !=null && index >=1 && index<=managers.size()){
                return managers.get(index-1).getId();
            }
        }

    }

    private void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }
}
