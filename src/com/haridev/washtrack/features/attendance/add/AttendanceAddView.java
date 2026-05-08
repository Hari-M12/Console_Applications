package com.haridev.washtrack.features.attendance.add;

import com.haridev.washtrack.data.dto.Attendance;
import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.util.ConsoleInput;
import com.haridev.washtrack.util.ParseHelper;

import java.util.Scanner;

public class AttendanceAddView {
    private final AttendanceAddModel attendanceAddModel;
    private final Scanner scanner;
    private final Employee employee;

    public AttendanceAddView(Employee employee){
        this.attendanceAddModel = new AttendanceAddModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }


    public void init() {

        System.out.println("Recording Attendance");

        while(true){
            System.out.println("1. Check In");
            System.out.println("2. Check Out");
            System.out.println("Choose your option");
            String choice = attendanceAddModel.parseChoice(scanner.nextLine());
            switch (choice){
                case "1":{
                    checkIn();
                    return;
                }
                case "2":{
                    checkOut();
                    return;
                }
                default:{
                    System.out.println("Select an valid Option");
                    break;
                }
            }
        }
    }

    public void checkIn(){
        Long id = employee.getId();
        Attendance attendance = attendanceAddModel.checkInAttendance(id);
        if (attendance == null) {
            System.out.println("Attendance already checkedIn for today.");
        } else {
            System.out.println("Checked in at "+ParseHelper.formatDateTime(attendance.getCheckinTime())+" on " + ParseHelper.formatDate(attendance.getDate()));
        }
    }

    public void checkOut(){
        Long id = employee.getId();
        Attendance attendance = attendanceAddModel.checkOutAttendance(id);
        if(attendance == null) showMessage("Check out failed");
        System.out.println("Checked out at "+ParseHelper.formatDateTime(attendance.getCheckoutTime())+" on " + ParseHelper.formatDate(attendance.getDate()));

    }

    public void showMessage(String s) {
        System.out.println(s);
    }
}
