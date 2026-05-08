package com.haridev.washtrack.features.employee.details;

import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.enums.Role;
import com.haridev.washtrack.util.ConsoleInput;
import com.haridev.washtrack.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

import static com.haridev.washtrack.util.ParseHelper.formatDate;

public class EmployeeDetailsView {

    private final EmployeeDetailsModel employeeDetailsModel;
    private final Scanner scanner;
    private final Employee employee;

    public EmployeeDetailsView(Employee employee) {
        this.employeeDetailsModel = new EmployeeDetailsModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }

    public void init() {

        System.out.println();
        System.out.println("Employee Details");
        Employee selectedEmployee = pickEmployee();
        if(selectedEmployee == null){
            showErrorMessage("No Employee found");
            return;
        }

        System.out.println(
                "---------------------------------------------------------------------------------------------");
        System.out.printf("%-12s %-20s %-25s %-12s %-12s %-10s %-15s %-20s %-10s %-15s%n",
                "ID", "Name", "Email", "Role", "Status", "Salary", "DOB", "Mobile", "CreatedAt", "Address");
        System.out.println(
                "---------------------------------------------------------------------------------------------");

        System.out.printf("%-12s %-20s %-25s %-12s %-12s %-10.2f %-15s %-20s %-10s %-15s%n",
                selectedEmployee.getId(),
                safe(selectedEmployee.getName()),
                safe(selectedEmployee.getEmailId()),
                selectedEmployee.getRole() == null ? "-" : selectedEmployee.getRole().name(),
                selectedEmployee.getStatus() == null ? "-" : selectedEmployee.getStatus().name(),
                selectedEmployee.getSalary() == null ? 0.0 : selectedEmployee.getSalary(),
                formatDate(selectedEmployee.getDob()),
                safe(selectedEmployee.getMobileNo()),
                formatDate(selectedEmployee.getJoinedDate()),
                truncate(safe(selectedEmployee.getAddress()), 15));
        System.out.println(
                "---------------------------------------------------------------------------------------------");


        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }

    Employee pickEmployee() {

        if (employee.getRole().equals(Role.EMPLOYEE)) {
            Employee fetchedEmployee = employeeDetailsModel.getEmployeeById(employee.getId());
            if (fetchedEmployee == null) {
                showErrorMessage("No employee found.");
                return null;
            }
            return fetchedEmployee;
        }else{
            List<Employee> totalEmployee = employeeDetailsModel.getAllEmployees(employee);
            if (totalEmployee==null) {
                return null;
            }
            System.out.println("#   Employee Id   Name                        Role");

            for (int i = 0; i < totalEmployee.size(); i++) {
                Employee e = totalEmployee.get(i);
                String row = String.format(
                        "%-3d %-13s %-27s %-9s",
                        (i + 1),
                        e.getId(),
                        truncate(safe(e.getName()), 27),
                        e.getRole() == null ? "-" : e.getRole().name()
                );

                System.out.println(row);
            }

            while (true) {
                System.out.println("Select Employee you want to view:");
                String choice = scanner.nextLine().trim();

                Long id = ParseHelper.parseNonNegativeLong(choice);

                Employee selected = employeeDetailsModel.getEmployeeById(id);
                if (selected != null)
                    return selected;
                showErrorMessage(" Employee is null! Invalid choice Try Again");
            }
        }
    }

    void showErrorMessage(String message) {
        System.out.println(message);
    }

    private String safe(String value) {
        return value == null ? "-" : value;
    }

    private String truncate(String value, int max) {
        if (value.length() <= max)
            return value;
        return value.substring(0, max - 1) + "~";
    }


}
