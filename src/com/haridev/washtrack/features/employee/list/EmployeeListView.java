package com.haridev.washtrack.features.employee.list;

import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.enums.Role;
import com.haridev.washtrack.util.ConsoleInput;

import java.util.List;
import java.util.Scanner;

import static com.haridev.washtrack.util.ParseHelper.formatDate;

public class EmployeeListView {

    private final EmployeeListModel employeeListModel;
    private final Scanner scanner;
    private final Employee employee;

    public EmployeeListView(Employee employee) {
        this.employeeListModel = new EmployeeListModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }

    public void init() {
        List<Employee> employees = employeeListModel.getAllEmployees(employee);
        System.out.println();
        System.out.println("All Employees");
        if (employees == null || employees.isEmpty()) {
            showErrorMessage("No employees yet.");
        } else {
            System.out.println("#   Employee Id   Name                        Role");

            for (int i = 0; i < employees.size(); i++) {
                Employee e = employees.get(i);
                String row = String.format(
                        "%-3d %-13s %-27s %-9s",
                        (i + 1),
                        e.getId(),
                        truncate(safe(e.getName()), 27),
                        e.getRole() == null ? "-" : e.getRole().name()
                );

                System.out.println(row);
            }
        }
        System.out.print("Press Enter to return:  ");
        scanner.nextLine();
    }

    void viewEmployees(){
        List<Employee> employees = employeeListModel.getAllEmployees(employee);
        System.out.println();
        System.out.println("All Employees");
        if (employees == null || employees.isEmpty()) {
            showErrorMessage("No employees yet.");
        } else {
            if(employee.getRole().equals(Role.MANAGER)){
                System.out.println("#   Employee Id   Name                        Email                           Role      Status     DOB          Mobile        Address");
                for (int i = 0; i < employees.size(); i++) {
                    Employee e = employees.get(i);
                    String row = String.format(
                            "%-3d %-13s %-27s %-31s %-9s %-10s %-12s %-20s",
                            (i + 1),
                            e.getId(),
                            truncate(safe(e.getName()), 27),
                            truncate(safe(e.getEmailId()), 31),
                            e.getRole() == null ? "-" : e.getRole().name(),
                            e.getDob() == null ? "-" : formatDate(e.getDob()),
                            safe(e.getMobileNo()),
                            truncate(safe(e.getAddress()), 20)
                    );
                    System.out.println(row);
                }
            }
        }
        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }


    private String safe(String value) {
        return value == null ? "-" : value;
    }

    private String truncate(String value, int max) {
        if (value.length() <= max) return value;
        return value.substring(0, max - 1) + "~";
    }

    public void showErrorMessage(String message) {
        System.out.println(message);
    }
}
