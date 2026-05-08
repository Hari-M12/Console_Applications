package com.haridev.washtrack.features.employee.details;

import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.enums.Role;

import java.util.List;

class EmployeeDetailsModel {
    private final EmployeeDetailsView employeeDetailsView;

    EmployeeDetailsModel(EmployeeDetailsView employeeDetailsView){
        this.employeeDetailsView = employeeDetailsView;
    }



    List<Employee> getAllEmployees(Employee employee) {
        Role currentEmployeeesRole = employee.getRole();
        if(currentEmployeeesRole == null ){
            employeeDetailsView.showErrorMessage("Role is Null");
            return null;
        }

        if(currentEmployeeesRole.equals(Role.OWNER))
            return WashTrackDB.getInstance().getAllManagerAndEmployees();
        else if (currentEmployeeesRole.equals(Role.MANAGER)) {
            return WashTrackDB.getInstance().getAllNonManagerEmployees();
        }
        return null;
    }

    Employee getEmployeeById(Long id) {
        if(id == null) return null;
        return WashTrackDB.getInstance().getEmployeeById(id);
    }
}
