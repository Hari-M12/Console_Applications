package com.zsgs.thiranx.features.task.add;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.enums.Priority;
import com.zsgs.thiranx.features.task.assign.TaskAssignView;
import com.zsgs.thiranx.util.ConsoleInput;
import com.zsgs.thiranx.util.ParseHelper;

import java.util.Scanner;

public class TaskAddView {

    private final TaskAddModel taskAddModel;
    private final Scanner scanner;
    private final Employee employee;

    public TaskAddView(Employee employee){
        this.taskAddModel = new TaskAddModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }



    public void init() {
        System.out.println();
        System.out.println("Add an Task");

        String title = promptTitle();
        String description = promptDescription();
        Priority priority = promptPriority();
        Long dueDate = promptDueDate();

        taskAddModel.createTask(employee, title, description, priority, dueDate);


    }

    private Priority promptPriority() {
        while (true) {
            System.out.println("Select priority:");
            System.out.println("1. P1");
            System.out.println("2. P2");
            System.out.println("3. P3");
            System.out.print("Choose an option: ");
            Priority priority = taskAddModel.parsePriority(scanner.nextLine());
            if (priority != null) return priority;
            System.out.println("Select a valid priority.");
        }
    }

    private String promptTitle() {
        while(true){
            System.out.println("Enter Task Title");
            String title = scanner.nextLine();
            String error = taskAddModel.validateTitle(title);
            if(error==null) return title.trim();
            showErrorMessage(error);
        }
    }

    private String promptDescription() {
        while (true) {
            System.out.print("Enter task description: ");
            String input = scanner.nextLine();
            String error = taskAddModel.validateDescription(input);
            if (error == null) return input.trim();
            showErrorMessage(error);
        }
    }

    private Long promptDueDate() {
        while (true) {
            System.out.print("Enter due date (dd-MM-yyyy): ");
            Long dueDate = taskAddModel.parseDueDate(scanner.nextLine());
            if (dueDate != null) return dueDate;
            System.out.println("Enter a valid date. Due date must be today or later.");
        }
    }

    private void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void onTaskCreateFailed(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void onTaskCreated(Task saved) {
        System.out.println();
        System.out.println("Task created successfully.");
        System.out.println("Task id: " + saved.getId());
        System.out.print("Do you want to assign this task now? (Y/N): ");
        if(ParseHelper.parseIsYes(scanner.nextLine())){
            new TaskAssignView(employee, saved).init();
        }else {
            System.out.println("Task saved without an assignee. Use Assign a task later.");
        }
    }
}
