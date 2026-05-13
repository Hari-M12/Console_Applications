package com.zsgs.thiranx.features.task.list;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.data.repository.ThiranXDB;

import java.util.ArrayList;
import java.util.List;

public class TaskListModel {
    private TaskListView taskListView;
    public TaskListModel(TaskListView taskListView){
        this.taskListView = taskListView;
    }

    List<Task> getTasksAssignedTo(Employee employee) {
        if (employee == null || employee.getId() == null) return new ArrayList<>();
        return ThiranXDB.getInstance().getAllTasksAssignedTo(employee.getId());
    }
}
