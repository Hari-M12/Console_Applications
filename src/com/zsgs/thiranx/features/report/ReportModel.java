package com.zsgs.thiranx.features.report;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.data.dto.TaskStatusHistory;
import com.zsgs.thiranx.data.repository.ThiranXDB;
import com.zsgs.thiranx.enums.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ReportModel {
    private final ReportView reportView;
    public ReportModel(ReportView reportView){
        this.reportView = reportView;
    }

    List<Employee> getAllReportees(Long managerId){
        return ThiranXDB.getInstance().getAllDirectReportees(managerId);
    }

    public void getTaskSummaryByStatus(Employee manager) {
        if(manager == null || manager.getId() == null) return;

        List<Task> tasks = ThiranXDB.getInstance().getAllTasksAssignedTo(manager.getId());

        Map<TaskStatus,Integer> countMap = new HashMap<>();
        for (Task task:tasks){
            TaskStatus status = task.getTaskStatus();
            countMap.put(status,countMap.getOrDefault(status,0)+1);
        }

        reportView.showTaskStatusReport( countMap);
    }

    public void getTaskSummaryByPriority(Employee manager){
        if(manager == null || manager.getId() == null) return;

        List<Task> tasks = ThiranXDB.getInstance().getAllTasksCreatedBy(manager.getId());

        Map<String, Integer> countMap = new HashMap<>();

        for(Task task : tasks){
            String priority = task.getPriority().name();
            countMap.put(priority, countMap.getOrDefault(priority, 0) + 1);
        }

        reportView.showTaskPriorityReport(countMap);
    }

    public void getEmployeeWiseSummary(Employee manager) {
        if(manager == null || manager.getId() == null) return;
        List<Task> tasks = ThiranXDB.getInstance().getAllTasksCreatedBy(manager.getId());
        Map<Long, Integer> countMap = new HashMap<>();

        for(Task task : tasks){
            if(task.getAssignedTo() == null) continue;

            Long empId = task.getAssignedTo();
            countMap.put(empId, countMap.getOrDefault(empId, 0) + 1);
        }

        reportView.showEmployeeWiseReport(countMap);
    }
}
