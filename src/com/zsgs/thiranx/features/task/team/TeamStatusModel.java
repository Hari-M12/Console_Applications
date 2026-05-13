package com.zsgs.thiranx.features.task.team;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.data.repository.ThiranXDB;
import com.zsgs.thiranx.util.ConsoleInput;

import java.util.List;
import java.util.Scanner;

public class TeamStatusModel {
    private final TeamStatusView teamStatusView;

    TeamStatusModel(TeamStatusView teamStatusView){
        this.teamStatusView = teamStatusView;
    }


    List<Employee> getDirectReports(Long managerId) {
        return ThiranXDB.getInstance().getAllDirectReportees(managerId);
    }

    public List<Task> getTasksFor(Long id) {
        return ThiranXDB.getInstance().getAllTasksAssignedTo(id);
    }
}
