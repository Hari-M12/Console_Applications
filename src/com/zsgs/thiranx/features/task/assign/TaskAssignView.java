package com.zsgs.thiranx.features.task.assign;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.enums.AssignMode;
import com.zsgs.thiranx.enums.Priority;
import com.zsgs.thiranx.util.ConsoleInput;
import com.zsgs.thiranx.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class TaskAssignView {

    private final TaskAssignModel taskAssignModel;
    private final Scanner scanner;
    private final Employee employee;
    private final AssignMode mode;
    private final Task preselectedTask;

    public TaskAssignView(Employee employee,AssignMode mode){
        this.taskAssignModel = new TaskAssignModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
        this.mode = mode;
        this.preselectedTask = null;

    }

    public TaskAssignView(Employee currentUser, Task preselectedTask) {
        this.taskAssignModel = new TaskAssignModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = currentUser;
        this.mode = AssignMode.MANAGER_ASSIGN;
        this.preselectedTask = preselectedTask;
    }

    public void init() {
        System.out.println();
        Task task = preselectedTask!=null ? preselectedTask : selectTask();
        if (task == null) return;

        List<Employee> assignees = taskAssignModel.listAvailableAssignees(employee,mode);
        if (assignees.isEmpty()) {
            System.out.println("No employees available to assign.");
            return;
        }

        Employee assignee = selectEmployee(assignees);
        if (assignee == null) return;

        System.out.print("Confirm assigning task '" + task.getTitle()
                + "' to " + assignee.getName() + "? (Y/N): ");
        if (!ParseHelper.parseIsYes(scanner.nextLine())) {
            System.out.println("Assignment cancelled.");
            return;
        }
        taskAssignModel.assign(task, assignee.getId());

    }

    private Employee selectEmployee(List<Employee> assignees) {
        while (true) {
            System.out.println("Select an employee:");
            for (int i = 0; i < assignees.size(); i++) {
                Employee e = assignees.get(i);
                System.out.println((i + 1) + ". " + e.getName() + " (" + e.getEmployeeId()
                        + ", " + (e.getRole() == null ? "-" : e.getRole().name()) + ")");
            }
            System.out.print("Choose an option: ");
            Integer index = ParseHelper.parseNonNegativeInt(scanner.nextLine());
            if (index != null && index >= 1 && index <= assignees.size()) {
                return assignees.get(index - 1);
            }
            System.out.println("Select a valid option.");
        }
    }

    private Task selectTask() {
        List<Task> tasks = taskAssignModel.listAssignableTasks(mode,employee);
        if (tasks.isEmpty()) {
            if (mode == AssignMode.MANAGER_ASSIGN) {
                System.out.println("No unassigned tasks to assign.");
            } else {
                System.out.println("You have no tasks to reassign.");
            }
            return null;
        }

        String header = mode == AssignMode.MANAGER_ASSIGN? "Select a task to assign:" : "Select a task to reassign:";
        while(true){
            System.out.println(header);
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                System.out.println((i + 1) + ". " + t.getTitle()
                        + " [" + (t.getPriority() == null ? "-" : t.getPriority().name())
                        + ", " + (t.getTaskStatus() == null ? "-" : t.getTaskStatus().name()) + "]");
            }
            System.out.println("choose an option");
            Integer index = ParseHelper.parseNonNegativeInt(scanner.nextLine());
            if(index!=null && index>=1 && index<=tasks.size()){
                return tasks.get(index-1);
            }
            System.out.println("Select an valid option");
        }
    }

    public void onAssignFailed(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void onAssignSuccessful(Task task,Long id) {
        System.out.println("Task "+task.getId() + "Assigned succcessfully to employee "+id);
    }
}
