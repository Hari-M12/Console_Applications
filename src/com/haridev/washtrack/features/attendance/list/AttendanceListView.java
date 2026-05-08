package com.haridev.washtrack.features.attendance.list;

import com.haridev.washtrack.data.dto.Attendance;
import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.enums.Role;
import com.haridev.washtrack.util.ConsoleInput;
import com.haridev.washtrack.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class AttendanceListView {
    private final AttendanceListModel attendanceListModel;
    private final Scanner scanner;
    private final Employee employee;

    public AttendanceListView(Employee employee) {
        this.attendanceListModel = new AttendanceListModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }

    public void init() {
        viewAllAttendance();
    }

    private void viewAllAttendance() {
        if (employee.getRole().equals(Role.MANAGER)) {
            System.out.println();
            System.out.println("Attendance Records for Employees");
            List<Attendance> attendances = attendanceListModel.getEmployeeAttendaceRecords();
            if (attendances == null) {
                showErrorMessage("No Attendance found");
                return;
            }

            System.out.println("#   Employee Id   Date          Check-in Time     Check-out Time");
            System.out.println("---------------------------------------------------------------");
            for (int i = 0; i < attendances.size(); i++) {
                Attendance att = attendances.get(i);
                String row = String.format(
                        "%-3d %-12s %-13s %-16s %-16s",
                        (i + 1),
                        att.getEmployeeId(),
                        ParseHelper.formatDate(att.getDate()),
                        att.getCheckinTime() == null ? "-" : ParseHelper.formatDateTime(att.getCheckinTime()),
                        att.getCheckoutTime() == null ? "-" : ParseHelper.formatDateTime(att.getCheckoutTime()));
                System.out.println(row);
            }
        } else {

            System.out.println();
            System.out.println("Attendance for All Employees and Manager");
            List<Attendance> attendances = attendanceListModel.getEmployeeManagerAttendance();
            if (attendances == null || attendances.isEmpty()) {
                showErrorMessage("No Attendance found");
                return;
            }
            System.out.println("#   Employee Id   Role       Date          Check-in Time     Check-out Time");
            System.out.println("----------------------------------------------------------------------------");
            for (int i = 0; i < attendances.size(); i++) {
                Attendance att = attendances.get(i);
                Employee emp = WashTrackDB.getInstance().getEmployeeById(att.getEmployeeId());
                String row = String.format(
                        "%-3d %-12s %-9s %-13s %-16s %-16s",
                        (i + 1),
                        att.getEmployeeId(),
                        emp.getRole().name(),
                        ParseHelper.formatDate(att.getDate()),
                        att.getCheckinTime() == null ? "-" : ParseHelper.formatDateTime(att.getCheckinTime()),
                        att.getCheckoutTime() == null ? "-" : ParseHelper.formatDateTime(att.getCheckoutTime()));
                System.out.println(row);
            }
        }

        System.out.println("Enter to exit");
        scanner.nextLine();
    }

    private void showErrorMessage(String message) {
        System.out.println(message);
    }

}
