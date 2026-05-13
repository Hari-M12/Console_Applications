package com.zsgs.thiranx.features.task.status;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Notification;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.data.dto.TaskStatusHistory;
import com.zsgs.thiranx.data.repository.ThiranXDB;
import com.zsgs.thiranx.enums.NotificationType;
import com.zsgs.thiranx.enums.TaskStatus;

import java.util.List;

class TaskStatusModel {
    private static final int MAX_REMARKS = 500;
    private final TaskStatusView taskStatusView;

    TaskStatusModel(TaskStatusView taskStatusView){
        this.taskStatusView = taskStatusView;
    }


     List<Task> getMyTasks(Employee employee) {
        return ThiranXDB.getInstance().getAllTasksAssignedTo(employee.getId());
    }

    TaskStatus parseStatus(String choice) {
        if (choice == null) return null;
        String c = choice.trim();
        if (c.equals("1") || c.equalsIgnoreCase("OPEN")) return TaskStatus.OPEN;
        if (c.equals("2") || c.equalsIgnoreCase("IN_PROGRESS")) return TaskStatus.IN_PROGRESS;
        if (c.equals("3") || c.equalsIgnoreCase("COMPLETED")) return TaskStatus.COMPLETED;
        if (c.equals("4") || c.equalsIgnoreCase("ON_HOLD")) return TaskStatus.ON_HOLD;
        if (c.equals("5") || c.equalsIgnoreCase("CANCELLED")) return TaskStatus.CANCELLED;
        if (c.equals("6") || c.equalsIgnoreCase("REOPENED")) return TaskStatus.REOPENED;
        return null;
    }

    String validateRemarks(String remarks) {
        if (remarks == null || remarks.trim().isEmpty()) return "Remarks cannot be empty";
        if (remarks.trim().length() > MAX_REMARKS) {
            return "Remarks cannot exceed " + MAX_REMARKS + " characters";
        }
        return null;
    }

    void updateStatus(Task task, TaskStatus newStatus, String remarks, Employee employee) {
        if (task == null || newStatus == null || employee == null) {
            taskStatusView.onUpdateFailed("Could not update status. Please try again.");
            return;
        }
        if (newStatus == task.getTaskStatus()) {
            taskStatusView.onUpdateFailed("New status must differ from current status.");
            return;
        }

        TaskStatus oldStatus = task.getTaskStatus();

        TaskStatusHistory history = new TaskStatusHistory();
        history.setTaskId(task.getId());
        history.setChangedBy(employee.getId());
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setRemarks(remarks == null ? "" : remarks.trim());
        ThiranXDB.getInstance().addStatusHistory(history);

        task.setTaskStatus(newStatus);
        if (newStatus == TaskStatus.COMPLETED) {
            task.setCompletedTime(System.currentTimeMillis());
        } else if (newStatus == TaskStatus.REOPENED) {
            task.setCompletedTime(null);
        }

        Task updated = ThiranXDB.getInstance().updateTask(task);
        if (updated == null) {
            taskStatusView.onUpdateFailed("Could not update status. Please try again.");
            return;
        }

        notifyCreator(updated, oldStatus, newStatus, employee);
        taskStatusView.onUpdateSuccessful(updated, oldStatus);
    }

    private void notifyCreator(Task task, TaskStatus oldStatus, TaskStatus newStatus, Employee changedBy) {
        Long creatorId = task.getAssignedBy();
        if (creatorId == null) return;
        if (changedBy != null && creatorId.equals(changedBy.getId())) return;

        Notification notification = new Notification();
        notification.setEmployeeId(creatorId);
        notification.setTaskId(task.getId());
        notification.setType(NotificationType.STATUS_UPDATED);
        String who = (changedBy == null || changedBy.getName() == null) ? "an employee" : changedBy.getName();
        notification.setMessage("Task '" + task.getTitle() + "' status changed from "
                + oldStatus + " to " + newStatus + " by " + who);
        ThiranXDB.getInstance().addNotification(notification);
    }
}
