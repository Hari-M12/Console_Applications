package com.zsgs.thiranx.data.repository;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Notification;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.data.dto.TaskStatusHistory;
import com.zsgs.thiranx.enums.EmployeeStatus;
import com.zsgs.thiranx.enums.Role;
import com.zsgs.thiranx.enums.TaskStatus;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThiranXDB {

    private final List<Employee> employeeList = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private final List<TaskStatusHistory> taskStatusHistories = new ArrayList<>();
    private final List<Notification> notifications = new ArrayList<>();
    private long employeePk = 0L;
    private long taskPk = 0L;
    private long notificationPk = 0L;
    private long taskStatusHistoryPk = 0L;

    private ThiranXDB(){

    }
    public static ThiranXDB thiranXDB;

    public static ThiranXDB getInstance(){
        if (thiranXDB == null ){
            thiranXDB = new ThiranXDB();
        }
        return thiranXDB;
    }

    public List<Employee> getAllEmployee(){
        return employeeList;
    }

    public Employee addEmployee(Employee employee){
        if (employee == null) return null;
        if (employee.getEmailId() == null || employee.getEmailId().trim().isEmpty()) return null;

        employeePk++;
        employee.setId(employeePk);
        employee.setEmployeeId(String.format(Locale.ROOT, "EMP%05d", employeePk));
        if (employee.getCreatedAt() == null) {
            employee.setCreatedAt(System.currentTimeMillis());
        }
        if (employee.getStatus() == null) {
            employee.setStatus(EmployeeStatus.ACTIVE);
        }
        if (employee.getRole() == null) {
            employee.setRole(Role.EMPLOYEE);
        }
        employeeList.add(employee);

        return employee;
    }

    public Employee authenticateEmployee(String email, String password){
        Employee employee = getEmployeeByEmail(email);
        if(employee == null) return null;
        if(!password.equals(employee.getPassword())) return null;
        return employee;
    }

    public Employee getEmployeeByEmail(String input) {
        if(input == null){
            return null;
        }
        String email = input.trim().toLowerCase(Locale.ROOT);
        for (Employee e:employeeList){
            if(e.getEmailId().trim().toLowerCase(Locale.ROOT).equals(email)){
                return e;
            }
        }
        return null;
    }

    public Employee getEmployeeById(Long id) {
        if (id == null) return null;
        for (Employee current : employeeList) {
            if (id.equals(current.getId())) return current;
        }
        return null;
    }


    public List<Employee> getActiveManagers() {
        List<Employee> result = new ArrayList<>();
        for (Employee e : employeeList){
            if(e.getRole() == Role.MANAGER && e.getStatus() == EmployeeStatus.ACTIVE){
                result.add(e);
            }
        }
        return result;

    }

    public List<Employee> getAllDirectReportees(Long managerId){
        List<Employee> reportees = new ArrayList<>();
        if (managerId == null) return reportees;
        for (Employee e : employeeList){
            if(managerId.equals(e.getReportingTo())){
                reportees.add(e);
            }
        }
        return reportees;
    }


    public Task addTask(Task task) {
        if(task==null) return null;
        taskPk++;
        task.setId(taskPk);
        long now = System.currentTimeMillis();
        if (task.getCreatedTime() == null) task.setCreatedTime(now);
        task.setUpdatedTime(now);
        if (task.getTaskStatus() == null) task.setTaskStatus(TaskStatus.OPEN);
        tasks.add(task);
        return task;
    }

    public Task updateTask(Task task) {
        if (task == null || task.getId() == null) return null;
        for(int i =0 ;i<tasks.size();i++){
            if(task.getId().equals(tasks.get(i).getId())){
                task.setUpdatedTime(System.currentTimeMillis());
                tasks.set(i,task);
                return task;
            }
        }
        return null;
    }

    public List<Task> getAllTasksCreatedBy(Long id) {
        List<Task> taskList = new ArrayList<>();
        if (id== null) return taskList;
        for (Task task: tasks){
            if(id.equals(task.getAssignedBy())){
                taskList.add(task);
            }
        }
        return taskList;
    }


    public List<Task> getAllUnassignedTasksCreatedBy(Long id) {
        List<Task> unAssigned = new ArrayList<>();
        if (id== null) return unAssigned;
        for (Task task: tasks){
            if(task.getAssignedTo()==null && id.equals(task.getAssignedBy())){
                unAssigned.add(task);
            }
        }
        return unAssigned;
    }

    public List<Task> getAllTasksAssignedTo(Long employeeId) {
        List<Task> taskList = new ArrayList<>();
        if (employeeId == null) return taskList;
        for (Task task: tasks){
            if(employeeId.equals(task.getAssignedTo())){
                taskList.add(task);
            }
        }
        return taskList;
    }

    public List<Employee> getAllEmployeesExcept(Long id) {
        List<Employee> employees = new ArrayList<>();

        for (Employee e : employeeList) {
            if(e.getStatus()!=EmployeeStatus.ACTIVE) continue;
            if(id!=null && id.equals(e.getId())) continue;
            employees.add(e);
        }
        return employees;
    }


    public Notification addNotification(Notification notification) {
        if (notification == null || notification.getEmployeeId() == null) return null;
        notificationPk++;
        notification.setId(notificationPk);
        if (notification.getCreatedTime() == null) {
            notification.setCreatedTime(System.currentTimeMillis());
        }
        if (notification.getIsRead() == null) {
            notification.setIsRead(Boolean.FALSE);
        }
        notifications.add(notification);
        return notification;
    }

    public TaskStatusHistory addStatusHistory(TaskStatusHistory history) {
        if (history == null || history.getTaskId() == null) return null;
        taskStatusHistoryPk++;
        history.setId(taskStatusHistoryPk);
        if (history.getChangedTime() == null) {
            history.setChangedTime(System.currentTimeMillis());
        }
        taskStatusHistories.add(history);
        return history;
    }


    public List<TaskStatusHistory> getTaskHistoryById(Long taskId) {
        List<TaskStatusHistory> histories = new ArrayList<>();
        if (taskId == null) return histories;
        for (TaskStatusHistory history : taskStatusHistories){
            if(taskId.equals(history.getTaskId())){
                histories.add(history);
            }
        }
        return histories;
    }

    public List<Notification> getNotificationsOf(Long id) {
        if(id==null) return null;
        List<Notification> result = new ArrayList<>();
        for (Notification notification: notifications){
            if (notification.getEmployeeId().equals(id)){
                result.add(notification);
            }
        }
        return result;
    }

    public int markNotificationsRead(Long userId) {
        if(userId==null) return 0;
        int count = 0;

        for (Notification current : notifications) {
            if (userId.equals(current.getEmployeeId())
                    && !Boolean.TRUE.equals(current.getIsRead())) {
                current.setIsRead(Boolean.TRUE);
                count++;
            }
        }
        return count;
    }
}
