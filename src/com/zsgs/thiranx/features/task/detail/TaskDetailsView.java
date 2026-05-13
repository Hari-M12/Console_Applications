package com.zsgs.thiranx.features.task.detail;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.data.dto.TaskStatusHistory;
import com.zsgs.thiranx.enums.TaskStatus;
import com.zsgs.thiranx.util.ConsoleInput;
import com.zsgs.thiranx.util.ParseHelper;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class TaskDetailsView {

    private final TaskDetailsModel taskDetailsModel;
    private final Scanner scanner;
    private final Employee employee;
    public TaskDetailsView(Employee employee){
        this.taskDetailsModel = new TaskDetailsModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }

    public void init(){
        System.out.println();
        System.out.println("Task Details\n");

        byte choice ;

        while (true){
            System.out.println("1.Task createdBy Me");
            System.out.println("2.My Tasks");
            System.out.println("3.exit");
            choice = ParseChoice(scanner.nextLine());
            if (choice == 1 || choice == 2) {
                break;
            } else {
                System.out.println("Invalid choice. Please select 1 or 2.\n");
            }
        }

        List<Task> tasks = taskDetailsModel.getAllMyTask(employee,choice);

        if (tasks.isEmpty()) {
            System.out.println("You have no tasks to view.");
            return;
        }

        Task task = pickTask(tasks);
        if (task == null) return;
        printTask(task);
        printTaskHistory(task);

        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }

    private void printTaskHistory(Task task) {
        System.out.println();
        System.out.println("Task Status History");
        List<TaskStatusHistory> histories = taskDetailsModel.getTaskHistoryFor(task.getId());
        if (histories.isEmpty()) {
            System.out.println("No status history yet.");
            return;
        }

        for (TaskStatusHistory entry : histories) {
            System.out.println(ParseHelper.formatDateTime(entry.getChangedTime())
                    + " | " + taskDetailsModel.getEmployeeName(entry.getChangedBy())
                    + " | " + nameOr(entry.getOldStatus()) + " -> " + nameOr(entry.getNewStatus())
                    + " | " + (entry.getRemarks() == null ? "" : entry.getRemarks()));
        }
    }

    private Task pickTask(List<Task> tasks) {
        while (true) {
            System.out.println("Select a task:");
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                System.out.println((i + 1) + ". " + t.getTitle() + " [" + nameOr(t.getTaskStatus()) + "]");
            }
            System.out.print("Choose an option: ");
            Integer index = ParseHelper.parseNonNegativeInt(scanner.nextLine());
            if (index != null && index >= 1 && index <= tasks.size()) {
                return tasks.get(index - 1);
            }
            System.out.println("Select a valid option.");
        }
    }

    byte ParseChoice(String choice) {
        if (choice == null) return 0;
        String c = choice.trim();
        if (c.equals("1") || c.equalsIgnoreCase("Task createdBy Me")) return 1;
        if (c.equals("2") || c.equalsIgnoreCase("My Tasks")) return 2;
//        if (c.equals("3") || c.equalsIgnoreCase("exit")) return 3;

        return 0;
    }

    private void printTask(Task task) {
        System.out.println();
        System.out.println("Id           : " + task.getId());
        System.out.println("Title        : " + task.getTitle());
        System.out.println("Description  : " + task.getDescription());
        System.out.println("Priority     : " + nameOr(task.getPriority()));
        System.out.println("Status       : " + nameOr(task.getTaskStatus()));
        System.out.println("Assigned by  : " + taskDetailsModel.getEmployeeName(task.getAssignedBy()));
        System.out.println("Assigned to  : " + taskDetailsModel.getEmployeeName(task.getAssignedTo()));
        System.out.println("Due date     : " + ParseHelper.formatDate(task.getDueDate()));
        System.out.println("Created at   : " + ParseHelper.formatDateTime(task.getCreatedTime()));
        System.out.println("Updated at   : " + ParseHelper.formatDateTime(task.getUpdatedTime()));
        System.out.println("Completed at : " + ParseHelper.formatDateTime(task.getCompletedTime()));
    }

    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }



}
