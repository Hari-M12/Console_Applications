package com.zsgs.thiranx.features.task.assign;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Notification;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.data.repository.ThiranXDB;
import com.zsgs.thiranx.enums.*;
import com.zsgs.thiranx.util.ParseHelper;

import java.util.ArrayList;
import java.util.List;

class TaskAssignModel {

    private static final int MIN_TITLE = 3;
    private static final int MAX_TITLE = 100;
    private static final int MAX_DESCRIPTION = 500;
    private final TaskAssignView taskAssignView;

    public TaskAssignModel(TaskAssignView taskAssignView){
        this.taskAssignView = taskAssignView;
    }


    public List<Task> listAssignableTasks(AssignMode mode, Employee employee) {
        if (mode == null || employee == null) return new ArrayList<>();
        if (mode == AssignMode.MANAGER_ASSIGN) {
            return ThiranXDB.getInstance().getAllUnassignedTasksCreatedBy(employee.getId());
        }
        return ThiranXDB.getInstance().getAllTasksAssignedTo(employee.getId());//need clarity
    }

    public List<Employee> listAvailableAssignees(Employee employee, AssignMode mode) {
        if(mode == AssignMode.MANAGER_ASSIGN){
            return ThiranXDB.getInstance().getAllEmployeesExcept(null);
        }
        List<Employee> allEmployees = ThiranXDB.getInstance().getAllEmployeesExcept(employee.getId());
        if (mode == AssignMode.EMPLOYEE_REASSIGN) {
            List<Employee> filtered = new ArrayList<>();
            for (Employee e : allEmployees) {
                if (e.getRole() == Role.EMPLOYEE) {
                    filtered.add(e);
                }
            }
            return filtered;
        }
        return allEmployees;
    }

    public void assign(Task task, Long id) {
        if (task == null || id == null) {
            taskAssignView.onAssignFailed("Could not update task. Please try again.");
            return;
        }

        task.setAssignedTo(id);

        Task updated = ThiranXDB.getInstance().updateTask(task);
        if (updated == null) {
            taskAssignView.onAssignFailed("Could not update task. Please try again.");
            return;
        }

        Notification notification = new Notification();
        notification.setEmployeeId(id);
        notification.setTaskId(updated.getId());
        notification.setType(NotificationType.TASK_ASSIGNED);
        notification.setMessage("You have been assigned task: " + updated.getTitle());
        ThiranXDB.getInstance().addNotification(notification);

        taskAssignView.onAssignSuccessful(updated,id);

    }
}
