package com.haridev.washtrack.features.employee.list;

import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.enums.Role;
import com.haridev.washtrack.features.employee.add.EmployeeAddView;
import com.haridev.washtrack.util.ConsoleInput;

import java.util.List;
import java.util.Scanner;

class EmployeeListModel {
    private final EmployeeListView employeeListView;

    EmployeeListModel(EmployeeListView employeeListView){
        this.employeeListView = employeeListView;
    }

    List<Employee> getAllEmployees(Employee employee) {
        Role currentEmployeeesRole = employee.getRole();
        if(currentEmployeeesRole == null ){
            employeeListView.showErrorMessage("Role is Null");
            return null;
        }

        if(currentEmployeeesRole.equals(Role.OWNER))
            return WashTrackDB.getInstance().getAllEmployees();
        else if (currentEmployeeesRole.equals(Role.MANAGER)) {
            return WashTrackDB.getInstance().getAllNonManagerEmployees();
        }
        return null;
    }
}
