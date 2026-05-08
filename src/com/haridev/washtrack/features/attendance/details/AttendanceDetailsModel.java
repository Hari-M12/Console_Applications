package com.haridev.washtrack.features.attendance.details;

import com.haridev.washtrack.data.dto.Attendance;
import com.haridev.washtrack.data.repository.WashTrackDB;

import java.util.List;

class AttendanceDetailsModel {
    private final AttendanceDetailsView attendanceDetailsView;

    AttendanceDetailsModel(AttendanceDetailsView attendanceDetailsView){
        this.attendanceDetailsView = attendanceDetailsView;
    }


    public List<Attendance> getMyAttendances(Long id) {
        if(id == null) return null;
        return WashTrackDB.getInstance().getMyAttendances(id);
    }

    public Attendance getMyTodaysAttendance(Long id) {
        if(id == null) return null;
        return WashTrackDB.getInstance().getTodayAttendanceOf(id);
    }
}
