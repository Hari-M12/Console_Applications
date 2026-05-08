package com.haridev.washtrack.features.attendance.add;

import com.haridev.washtrack.data.dto.Attendance;
import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.util.ParseHelper;

import java.util.List;

class AttendanceAddModel {
    private final AttendanceAddView attendanceAddView;

    AttendanceAddModel(AttendanceAddView attendanceAddView){
        this.attendanceAddView = attendanceAddView;
    }

    public Attendance checkInAttendance(Long id) {
        if(id==null) return null;

        if(isAttendanceRecordedForToday(id)){
            return null;
        }
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(id);
        attendance.setDate(System.currentTimeMillis());
        attendance.setCheckinTime(System.currentTimeMillis());
        attendance.setCheckoutTime(null);

        return WashTrackDB.getInstance().recordAttendanceFor(attendance);
    }

    private boolean isAttendanceRecordedForToday(Long employeeId) {
        List<Attendance> attendanceList = WashTrackDB.getInstance().getAllAttendanceOf(employeeId);
        String today = ParseHelper.formatDate(System.currentTimeMillis());
        for (Attendance attendance:attendanceList){
            String date = ParseHelper.formatDate(attendance.getDate());
            if(today.equals(date)){
                return true;
            }
        }
        return false;
    }

    public Attendance checkOutAttendance(Long id) {

        if(id==null) return null;

        if(!isAttendanceRecordedForToday(id)){
            attendanceAddView.showMessage("Today attendace not checked in so,check in first");
            checkInAttendance(id);
        }
        Attendance attendance = WashTrackDB.getInstance().getTodayAttendanceOf(id);
        attendance.setCheckoutTime(System.currentTimeMillis());
        return WashTrackDB.getInstance().updateAttendance(attendance);
    }

    public String parseChoice(String s) {
        String input = s.trim();
        if(input.equals("1") || input.equalsIgnoreCase("Check In")) return "1";
        if(input.equals("2") || input.equalsIgnoreCase("Check Out")) return "2";
        if(input.equals("3") || input.equalsIgnoreCase("Exit")) return "3";
        return "Invalid Choice";
    }


}
