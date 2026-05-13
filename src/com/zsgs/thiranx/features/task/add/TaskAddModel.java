package com.zsgs.thiranx.features.task.add;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.data.repository.ThiranXDB;
import com.zsgs.thiranx.enums.Priority;
import com.zsgs.thiranx.enums.TaskStatus;
import com.zsgs.thiranx.util.ParseHelper;

class TaskAddModel {

    private static final int MIN_TITLE = 3;
    private static final int MAX_TITLE = 100;
    private static final int MAX_DESCRIPTION = 500;
    private final TaskAddView taskAddView;

    public TaskAddModel(TaskAddView taskAddView){
        this.taskAddView = taskAddView;
    }


    public String validateTitle(String input) {
        if(input== null || input.trim().isEmpty()) return "Title cannot be empty";
        String title = input.trim();
        int titleLength = title.length();
        if(titleLength < MIN_TITLE || titleLength > MAX_TITLE){
            return "Title should be between "+ MIN_TITLE + " and " + MAX_TITLE;
        }
        return null;
    }

    String validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) return "Description cannot be empty";
        if (description.trim().length() > MAX_DESCRIPTION) {
            return "Description cannot exceed " + MAX_DESCRIPTION + " characters";
        }
        return null;
    }

    Priority parsePriority(String choice) {
        if (choice == null) return null;
        String c = choice.trim();
        if (c.equals("1") || c.equalsIgnoreCase("P1")) return Priority.P1;
        if (c.equals("2") || c.equalsIgnoreCase("P2")) return Priority.P2;
        if (c.equals("3") || c.equalsIgnoreCase("P3")) return Priority.P3;
        return null;
    }
    Long parseDueDate(String dueDateText) {
        Long dueDate = ParseHelper.parseDate(dueDateText);
        if (dueDate==null) return null;
        if(ParseHelper.isTodayOrFuture(dueDate)) return dueDate;
        return null;
    }

    public void createTask(Employee employee, String title, String description, Priority priority, Long dueDate) {
        Task task = new Task();
        task.setTitle(title.trim());
        task.setDescription(description.trim());
        task.setPriority(priority);
        task.setDueDate(dueDate);
        task.setAssignedBy(employee!=null ? employee.getId() : null);
        task.setAssignedTo(null);
        task.setTaskStatus(TaskStatus.OPEN);

        Task saved = ThiranXDB.getInstance().addTask(task);

        if (saved == null) {
            taskAddView.onTaskCreateFailed("Could not create task. Please try again.");
            return;
        }
        taskAddView.onTaskCreated(saved);

    }
}
