package com.zsgs.thiranx.features.report;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.repository.ThiranXDB;
import com.zsgs.thiranx.enums.TaskStatus;
import com.zsgs.thiranx.util.ConsoleInput;

import java.util.Map;
import java.util.Scanner;

public class ReportView {

    private final ReportModel reportModel;
    private final Scanner scanner;
    private final Employee employee;

    public ReportView(Employee employee) {
        this.reportModel = new ReportModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;

    }

    public void init() {
        System.out.println();
        System.out.println("Report view");
        System.out.println("1. Task Summary By Status");
        System.out.println("2. Task Summary By Priority");
        System.out.println("3. Employee-wise Task Summary");
        System.out.println("4. Back");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1": {
                reportModel.getTaskSummaryByStatus(employee);
                break;
            }
            case "2": {
                reportModel.getTaskSummaryByPriority(employee);
                break;
            }
            case "3": {
                reportModel.getEmployeeWiseSummary(employee);
                break;
            }
            case "4": {
                return;
            }
            default:
                System.out.println("Default Choice");

        }

    }

    public void showTaskStatusReport(Map<TaskStatus, Integer> data) {
        System.out.println("\nTask Summary By Status:");
        for (Map.Entry<TaskStatus, Integer> entry : data.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void showTaskPriorityReport(Map<String, Integer> data) {
        System.out.println("\nTask Summary By Priority:");
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void showEmployeeWiseReport(Map<Long, Integer> data) {
        System.out.println("\nEmployee-wise Task Summary:");

        for (Map.Entry<Long, Integer> entry : data.entrySet()) {
            Long empId = entry.getKey();
            int count = entry.getValue();
            Employee emp = ThiranXDB.getInstance().getEmployeeById(empId);
            String name = (emp != null) ? emp.getName() : "Unknown";
            System.out.println(name + " (" + empId + ") : " + count);
        }
    }
}
