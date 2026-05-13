package com.zsgs.thiranx.features.employee.reportee;


import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.util.ConsoleInput;

import java.util.List;
import java.util.Scanner;

public class ReporteeListView {

    private final ReporteeListModel reporteeListModel;
    private final Scanner scanner;
    private final Employee manager;

    public ReporteeListView(Employee manager) {
        this.reporteeListModel = new ReporteeListModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.manager = manager;
    }

    public void init() {
        System.out.println();
        System.out.println("My Reportees");
        Long managerId = (manager == null) ? null : manager.getId();
        List<Employee> reportees = reporteeListModel.getReportees(managerId);
        if (reportees.isEmpty()) {
            System.out.println("You have no reporting employees.");
        } else {
            System.out.println("#   Employee Id   Name                        Email                           Role      Status");
            for (int i = 0; i < reportees.size(); i++) {
                Employee e = reportees.get(i);
                String row = String.format(
                        "%-3d %-13s %-27s %-31s %-9s %s",
                        (i + 1),
                        safe(e.getEmployeeId()),
                        truncate(safe(e.getName()), 27),
                        truncate(safe(e.getEmailId()), 31),
                        e.getRole() == null ? "-" : e.getRole().name(),
                        e.getStatus() == null ? "-" : e.getStatus().name());
                System.out.println(row);
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
}
