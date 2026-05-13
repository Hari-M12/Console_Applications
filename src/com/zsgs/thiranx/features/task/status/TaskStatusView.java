package com.zsgs.thiranx.features.task.status;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.enums.TaskStatus;
import com.zsgs.thiranx.util.ConsoleInput;
import com.zsgs.thiranx.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class TaskStatusView {
    private final TaskStatusModel taskStatusModel;
    private final Scanner scanner;
    private final Employee employee;

    public TaskStatusView(Employee employee){
        this.taskStatusModel = new TaskStatusModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }

    public void init() {
        System.out.println();
        System.out.println("Update Task Status");
        List<Task> taskList = taskStatusModel.getMyTasks(employee);
        if (taskList.isEmpty()) {
            System.out.println("You have no tasks to update.");
            return;
        }
        Task task = selectTask(taskList);
        if (task == null) return;

        System.out.println("Current status: " + nameOr(task.getTaskStatus()));
        TaskStatus newStatus = pickStatus();
        if (newStatus == null) return;

        String remarks = promptRemarks();
        taskStatusModel.updateStatus(task, newStatus, remarks, employee);


    }

    private String promptRemarks() {
        while (true) {
            System.out.print("Enter remarks: ");
            String input = scanner.nextLine();
            String error = taskStatusModel.validateRemarks(input);
            if (error == null) return input.trim();
            System.out.println(error);
        }
    }

    private Task selectTask(List<Task> tasks) {
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

    private TaskStatus pickStatus() {
        while (true) {
            System.out.println("Select new status:");
            System.out.println("1. OPEN");
            System.out.println("2. IN_PROGRESS");
            System.out.println("3. COMPLETED");
            System.out.println("4. ON_HOLD");
            System.out.println("5. CANCELLED");
            System.out.println("6. REOPENED");
            System.out.print("Choose an option: ");
            TaskStatus status = taskStatusModel.parseStatus(scanner.nextLine());
            if (status != null) return status;
            System.out.println("Select a valid status.");
        }
    }



    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }


    public void onUpdateFailed(String errorMessage) {
        System.out.println(errorMessage);
    }

    void onUpdateSuccessful(Task task, TaskStatus oldStatus) {
        System.out.println("Status updated from " + nameOr(oldStatus) + " to " + nameOr(task.getTaskStatus()) + ".");
    }
}
