package com.haridev.washtrack.features.home;

import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.enums.Role;

class HomeModel {
    private final HomeView homeView;

    HomeModel(HomeView homeView) {
        this.homeView = homeView;
    }

    void init(Employee employee) {
        if (employee == null || employee.getRole() == null) {
            homeView.showUnauthorized();
            return;
        }
        if (employee.getRole().equals(Role.OWNER) || employee.getRole() == Role.MANAGER) {
            homeView.showCommonMenu();
        } else {
            homeView.showEmployeeMenu();
        }
    }
}
