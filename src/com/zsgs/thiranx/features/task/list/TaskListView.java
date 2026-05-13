package com.zsgs.thiranx.features.task.list;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.util.ConsoleInput;
import com.zsgs.thiranx.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class TaskListView {
    private final TaskListModel taskListModel;
    private final Scanner scanner;
    private final Employee employee;

    public TaskListView(Employee employee){
        this.taskListModel = new TaskListModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }

    public void init() {
        System.out.println();
        System.out.println("My Tasks");
        List<Task> tasks = taskListModel.getTasksAssignedTo(employee);
        if (tasks.isEmpty()) {
            System.out.println("You have no assigned tasks.");
        } else {
            System.out.println("#   Id     Title                          Priority   Status        Due Date");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                String row = String.format(
                        "%-3d %-6s %-30s %-10s %-13s %s",
                        (i + 1),
                        task.getId() == null ? "-" : task.getId(),
                        truncate(safe(task.getTitle()), 30),
                        nameOr(task.getPriority()),
                        nameOr(task.getTaskStatus()),
                        ParseHelper.formatDate(task.getDueDate()));
                System.out.println(row);
            }
        }
        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }

    private String safe(String value) {
        return value == null ? "-" : value;
    }

    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }

    private String truncate(String value, int max) {
        if (value.length() <= max) return value;
        return value.substring(0, max - 1) + "~";
    }
}
