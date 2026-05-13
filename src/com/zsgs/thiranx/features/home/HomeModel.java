package com.zsgs.thiranx.features.home;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.enums.Role;

class HomeModel {
    private HomeView homeView;
    public HomeModel(HomeView homeView){
        this.homeView = homeView;
    }

    public void ini(Employee employee) {
        if(employee == null || employee.getRole() == null){
            homeView.showUnauthorized();
            return;
        }
        if(employee.getRole() == Role.MANAGER){
            homeView.showManagerMenu();
        }else {
            homeView.showEmployeeMenu();
        }
    }
}
