package com.zsgs.thiranx.features.notification;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Notification;
import com.zsgs.thiranx.util.ConsoleInput;
import com.zsgs.thiranx.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class NotificationView {
    private final NotificationModel notificationModel;
    private final Scanner scanner;
    private final Employee employee;
    public NotificationView(Employee employee){
        this.notificationModel = new NotificationModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }

    public void init(){
        System.out.println();
        System.out.println("Notification");
        Long userId = (employee == null) ? null : employee.getId();
        List<Notification> notificationList = notificationModel.getNotificationsFor(userId);
        if (notificationList.isEmpty()) {
            System.out.println("You have no notifications.");
            System.out.print("Press Enter to return: ");
            scanner.nextLine();
            return;
        }

        int unreadCount = 0;
        for (int i = 0; i < notificationList.size(); i++) {
            Notification n = notificationList.get(i);
            boolean unread = !Boolean.TRUE.equals(n.getIsRead());
            if (unread) unreadCount++;
            String marker = unread ? "*" : " ";
            System.out.println((i + 1) + ". [" + marker + "] "
                    + ParseHelper.formatDateTime(n.getCreatedTime())
                    + " | " + nameOr(n.getType())
                    + " | " + safe(n.getMessage()));
        }

        if (unreadCount > 0) {
            System.out.print("Mark all as read? (Y/N): ");
            if (ParseHelper.parseIsYes(scanner.nextLine())) {
                int marked = notificationModel.markAllRead(userId);
                System.out.println(marked + " notification(s) marked as read.");
            }
        } else {
            System.out.print("Press Enter to return: ");
            scanner.nextLine();
        }
    }

    private String safe(String value) {
        return value == null ? "-" : value;
    }

    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }

}
