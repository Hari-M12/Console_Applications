package com.zsgs.thiranx.features.employee.reportee;


import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.repository.ThiranXDB;

import java.util.List;

class ReporteeListModel {

    private final ReporteeListView reporteeListView;

    ReporteeListModel(ReporteeListView reporteeListView) {
        this.reporteeListView = reporteeListView;
    }

    List<Employee> getReportees(Long managerId) {
        return ThiranXDB.getInstance().getAllDirectReportees(managerId);
    }
}
