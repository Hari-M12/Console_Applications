package com.zsgs.thiranx.features.notification;

import com.zsgs.thiranx.data.dto.Notification;
import com.zsgs.thiranx.data.repository.ThiranXDB;

import java.util.ArrayList;
import java.util.List;

class NotificationModel {
    private final NotificationView notificationView;

    NotificationModel(NotificationView notificationView){
        this.notificationView = notificationView;
    }



    public List<Notification> getNotificationsFor(Long id) {
        if(id==null) return new ArrayList<>();
        return ThiranXDB.getInstance().getNotificationsOf(id);
    }

    public int markAllRead(Long userId) {
        return ThiranXDB.getInstance().markNotificationsRead(userId);
    }
}
