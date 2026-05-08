package com.haridev.washtrack.features.attendance.details;

import com.haridev.washtrack.data.dto.Attendance;
import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.enums.Role;
import com.haridev.washtrack.util.ConsoleInput;
import com.haridev.washtrack.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class AttendanceDetailsView {
    private final AttendanceDetailsModel attendanceDetailsModel;
    private final Scanner scanner;
    private final Employee employee;

    public AttendanceDetailsView(Employee employee){
        this.attendanceDetailsModel = new AttendanceDetailsModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }

    public void init(){
        viewMyAttendanceHistory();
    }

    private void viewMyAttendanceHistory() {

        System.out.println();
        System.out.println("My Attendance");

        if(employee.getRole().equals(Role.EMPLOYEE)){

            Attendance todayAttendance = attendanceDetailsModel.getMyTodaysAttendance(employee.getId());
            System.out.println("-------------------------------------------------------------");
            System.out.printf("%-12s %-12s %-20s %-20s%n",
                    "Date", "ID", "Check-in", "Check-out");
            System.out.println("-------------------------------------------------------------");

            if (todayAttendance == null) {
                System.out.println("No attendance found for today.");
            } else {
                System.out.printf("%-12s %-12s %-20s %-20s%n",
                        ParseHelper.formatDate(todayAttendance.getDate()),
                        todayAttendance.getEmployeeId(),
                        todayAttendance.getCheckinTime() == null ? "-" : ParseHelper.formatDateTime(todayAttendance.getCheckinTime()),
                        todayAttendance.getCheckoutTime() == null ? "-" :ParseHelper.formatDateTime(todayAttendance.getCheckoutTime())
                );
            }
        }else {
            List<Attendance> attendances = attendanceDetailsModel.getMyAttendances(employee.getId());
            if(attendances==null) {
                showErrorMessage("No Attendance found");
                return;
            }

            System.out.println("#   Date          Check-in Time     Check-out Time     Worked Minutes");
            System.out.println("---------------------------------------------------------------------");

            for (int i = 0; i < attendances.size(); i++) {
                Attendance att = attendances.get(i);

                String row = String.format(
                        "%-3d %-13s %-16s %-16s %-15s",
                        (i + 1),
                        ParseHelper.formatDate(att.getDate()),
                        att.getCheckinTime() == null ? "-" : ParseHelper.formatDateTime(att.getCheckinTime()),
                        att.getCheckoutTime() == null ? "-" : ParseHelper.formatDateTime(att.getCheckoutTime()),
                        (att.getCheckinTime() != null && att.getCheckoutTime() != null)
                                ? ((att.getCheckoutTime() - att.getCheckinTime()) / (1000 * 60)) + " mins" : "-"
                );
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
