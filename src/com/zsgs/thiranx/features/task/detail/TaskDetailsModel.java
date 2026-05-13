package com.zsgs.thiranx.features.task.detail;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.data.dto.TaskStatusHistory;
import com.zsgs.thiranx.data.repository.ThiranXDB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TaskDetailsModel {
    private final TaskDetailsView taskDetailsView;

    TaskDetailsModel(TaskDetailsView taskDetailsView){
        this.taskDetailsView = taskDetailsView;
    }


    List<Task> getAllMyTask(Employee employee,byte choice) {
        List<Task> tasks = new ArrayList<>();
        if (employee == null || employee.getId() == null) return tasks;

        Long employeeId = employee.getId();
        Set<Long> seen = new HashSet<>();
        if(choice == 1){
            for (Task task: ThiranXDB.getInstance().getAllTasksCreatedBy(employee.getId())){
                if(task.getId()!=null && seen.add(task.getId())) tasks.add(task);
            }
        }else if(choice == 2){
            for (Task task: ThiranXDB.getInstance().getAllTasksAssignedTo(employee.getId())){
                if(task.getId()!=null && seen.add(task.getId())) tasks.add(task);
            }
        }
        return tasks;
    }

    public String getEmployeeName(Long assignedBy) {
        if(assignedBy==null) return "-";
        Employee employee = ThiranXDB.thiranXDB.getEmployeeById(assignedBy);
        return employee==null || employee.getName() == null ? "-" : employee.getName();
    }

    public List<TaskStatusHistory> getTaskHistoryFor(Long taskId) {
        return ThiranXDB.getInstance().getTaskHistoryById(taskId);
    }


}
