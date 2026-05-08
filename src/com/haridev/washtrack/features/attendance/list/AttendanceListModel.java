package com.haridev.washtrack.features.attendance.list;

import com.haridev.washtrack.data.dto.Attendance;
import com.haridev.washtrack.data.repository.WashTrackDB;

import java.util.List;

class AttendanceListModel {
    private final AttendanceListView attendanceListView;

    AttendanceListModel(AttendanceListView attendanceListView){
        this.attendanceListView = attendanceListView;
    }

    public List<Attendance> getEmployeeAttendaceRecords() {
        return WashTrackDB.getInstance().getAllEmployeesAttendance();
    }

    public List<Attendance> getEmployeeManagerAttendance() {
        return WashTrackDB.getInstance().getAllEmployeesManagersAttendance();
    }
}
