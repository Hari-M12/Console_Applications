package com.haridev.washtrack.features.home;
import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.enums.Role;
import com.haridev.washtrack.features.attendance.add.AttendanceAddView;
import com.haridev.washtrack.features.attendance.details.AttendanceDetailsView;
import com.haridev.washtrack.features.attendance.list.AttendanceListView;
import com.haridev.washtrack.features.customer.add.CustomerAddView;
import com.haridev.washtrack.features.customer.details.CustomerDetailsView;
import com.haridev.washtrack.features.customer.list.CustomerListView;
import com.haridev.washtrack.features.employee.add.EmployeeAddView;
import com.haridev.washtrack.features.employee.details.EmployeeDetailsView;
import com.haridev.washtrack.features.employee.list.EmployeeListView;
import com.haridev.washtrack.features.report.ReportDashboardView;
import com.haridev.washtrack.features.washrecord.add.WashRecordAddView;
import com.haridev.washtrack.features.washrecord.list.WashListView;
import com.haridev.washtrack.features.washrecord.payment.status.PaymentStatusUpdateView;
import com.haridev.washtrack.features.washrecord.status.WashRecordStatusUpdateView;
import com.haridev.washtrack.util.ConsoleInput;

import java.util.Scanner;

public class HomeView {

    private final HomeModel homeModel;
    private final Employee employee;
    private final Scanner scanner;

    public HomeView(Employee employee) {
        this.homeModel = new HomeModel(this);
        this.employee = employee;
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        homeModel.init(employee);
    }

    void showUnauthorized() {
        System.out.println("Your account role is not set. Contact your administrator.");
    }

    void showCommonMenu() {
        while (true) {
            System.out.println();
            if (employee.getRole().equals(Role.OWNER)) {
                System.out.println("Owner Home");
            } else if (employee.getRole().equals(Role.MANAGER)) {
                System.out.println("Manager Home");
            }
            int option = 1;
            if (employee.getRole().equals(Role.OWNER)) {
                System.out.println(option++ + ". Add Employee");
            }

            System.out.println(option++ + ". View Employee List");
            System.out.println(option++ + ". View One Employee");
            System.out.println(option++ + ". Record Today's Attendance");
            System.out.println(option++ + ". View Employees Attendance Records");
            System.out.println(option++ + ". My Attendance Today");
            System.out.println(option++ + ". Add Customer");
            System.out.println(option++ + ". View All Customers");
            System.out.println(option++ + ". View Customer Details");
            System.out.println(option++ + ". Save WashRecord and Assign it");
            System.out.println(option++ + ". WashRecord History");
            System.out.println(option++ + ". Update WashRecord Status");
            System.out.println(option++ + ". Update Payment Status");

            if (employee.getRole().equals(Role.OWNER)) {
                System.out.println(option++ + ". View employee report");
                System.out.println(option++ + ". Sign out");
            } else if (employee.getRole().equals(Role.MANAGER)) {
                System.out.println(option++ + ". Sign out");
            }
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    new EmployeeAddView(employee).init();
                    break;
                case "2":
                    new EmployeeListView(employee).init();
                    break;
                case "3":
                    new EmployeeDetailsView(employee).init();
                    break;
                case "4":
                    new AttendanceAddView(employee).init();
                    break;
                case "5":
                    new AttendanceListView(employee).init();
                    break;
                case "6":
                    new AttendanceDetailsView(employee).init();
                    break;
                case "7":
                    new CustomerAddView().init();
                    break;
                case "9":
                    new CustomerDetailsView().init();
                    break;
                case "8":
                    new CustomerListView().init();
                    break;
                case "10":
                    new WashRecordAddView().init();
                    break;
                case "11":
                    new WashListView().init();
                    break;
                case "12":
                    new WashRecordStatusUpdateView(employee).init();
                    break;
                case "13":
                    new PaymentStatusUpdateView().init();
                    break;
                case "14":
                    if (employee.getRole().equals(Role.OWNER)) {
                        new ReportDashboardView().init();
                    } else {
                        System.out.println("You have been signed out.");
                        return;
                    }
                    break;
                case "15":
                    if (employee.getRole().equals(Role.OWNER)) {
                        System.out.println("You have been signed out.");
                        return;
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    void showEmployeeMenu() {
        while (true) {
            System.out.println();
            System.out.println("Employee Menu");
            System.out.println("1.  View My Details");
            System.out.println("2.  Record Today's Attendance");
            System.out.println("3.  My Attendance Today");
            System.out.println("4. Update WashRecord Status");
            System.out.println("5. SignOut");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {

                case "1":
                    new EmployeeDetailsView(employee).init();
                    break;
                case "2":
                    new AttendanceAddView(employee).init();
                    break;
                case "3":
                    new AttendanceDetailsView(employee).init();
                    break;
                case "4":
                    new WashRecordStatusUpdateView(employee).init();
                    break;
                case "5":
                    System.out.println("You have been signed out.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

}
