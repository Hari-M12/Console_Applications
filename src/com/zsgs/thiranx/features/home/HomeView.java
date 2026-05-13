package com.zsgs.thiranx.features.home;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.enums.AssignMode;
import com.zsgs.thiranx.features.employee.add.EmployeeAddView;
import com.zsgs.thiranx.features.employee.list.EmployeeListView;
import com.zsgs.thiranx.features.employee.reportee.ReporteeListView;
import com.zsgs.thiranx.features.notification.NotificationView;
import com.zsgs.thiranx.features.report.ReportView;
import com.zsgs.thiranx.features.task.add.TaskAddView;
import com.zsgs.thiranx.features.task.assign.TaskAssignView;
import com.zsgs.thiranx.features.task.detail.TaskDetailsView;
import com.zsgs.thiranx.features.task.list.TaskListView;
import com.zsgs.thiranx.features.task.status.TaskStatusView;
import com.zsgs.thiranx.features.task.team.TeamStatusView;
import com.zsgs.thiranx.util.ConsoleInput;

import java.util.Scanner;

public class HomeView {
    private final HomeModel homeModel;
    private final Scanner scanner;
    private final Employee employee;

    public HomeView(Employee employee) {
        this.homeModel = new HomeModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }

    public void init() {
        homeModel.ini(employee);
    }

    void showUnauthorized() {
        System.out.println("Your account role is not set. Contact your administrator.");
    }

    public void showManagerMenu() {

        while (true) {
            System.out.println();
            System.out.println("Manager Home");
            System.out.println("1.View all employees");
            System.out.println("2.View reportees");
            System.out.println("3.Add employee");
            System.out.println("4.Add new task");
            System.out.println("5.Assign a task");
            System.out.println("6.View team status");
            System.out.println("7.Update my task status");
            System.out.println("8.View task details");
            System.out.println("9.Notifications");
            System.out.println("10.View reports");
            System.out.println("11.Sign out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": {
                    new EmployeeListView().init();
                    break;
                }
                case "2": {
                    new ReporteeListView(employee).init();
                    break;
                }
                case "3": {
                    new EmployeeAddView(employee).init();
                    break;
                }
                case "4": {
                    new TaskAddView(employee).init();
                    break;
                }
                case "5": {
                    new TaskAssignView(employee, AssignMode.MANAGER_ASSIGN).init();
                    break;
                }
                case "6": {
                    new TeamStatusView(employee).init();
                    break;
                }
                case "7": {
                    new TaskStatusView(employee).init();
                    break;
                }
                case "8": {
                    new TaskDetailsView(employee).init();// changes done
                    break;
                }
                case "9": {
                    new NotificationView(employee).init();// changes done
                    break;
                }
                case "10": {
                    new ReportView(employee).init();
                    break;
                }
                case "11": {
                    System.out.println("You have been signed out.");
                    return;
                }
                default:
                    System.out.println("Invalid option. Please try again.");

            }
        }
    }

    void showEmployeeMenu() {
        while (true) {
            System.out.println();
            System.out.println("Employee Home");
            System.out.println("1. My tasks");
            System.out.println("2. Update task status");
            System.out.println("3. Reassign a task");
            System.out.println("4. View task details");
            System.out.println("5. Notifications");
            System.out.println("6. Sign out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    new TaskListView(employee).init();
                    break;
                case "2":
                    new TaskStatusView(employee).init();
                    break;
                case "3":
                    new TaskAssignView(employee, AssignMode.EMPLOYEE_REASSIGN).init();
                    break;
                case "4":
                    new TaskDetailsView(employee).init();
                    break;
                case "5":
                    new NotificationView(employee).init();
                    break;
                case "6":
                    System.out.println("You have been signed out.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
