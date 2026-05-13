package com.zsgs.thiranx.features.signup;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.repository.ThiranXDB;
import com.zsgs.thiranx.enums.EmployeeStatus;
import com.zsgs.thiranx.enums.Role;
import com.zsgs.thiranx.util.ParseHelper;

import java.util.List;
import java.util.regex.Pattern;

class SignUpModel {

    private final int MIN_NAME_LENGTH = 3;
    private final int MAX_NAME_LENGTH = 50;
    private final int MIN_AGE_YEARS = 18;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d).{8,}$");

    private  SignUpView signUpView;


     SignUpModel(SignUpView signUpView){
        this.signUpView = signUpView;
    }

     static Role parseRole(String input) {
        if (input == null) return null;
        String choice = input.trim();
        if (choice.equals("1") || choice.equalsIgnoreCase("Manager")) return Role.MANAGER;
        if (choice.equals("2") || choice.equalsIgnoreCase("Employee")) return Role.EMPLOYEE;
        return null;
    }

     boolean isFirstEmployee() {
        return ThiranXDB.getInstance().getAllEmployee().isEmpty();
    }

    List<Employee> getActiveManagers(){
         return ThiranXDB.getInstance().getActiveManagers();
    }

    String validateName(String input){
        if(input == null || input.trim().isEmpty()){
            return "Name cannot be empty";
        }
        String name = input.trim();
        int length  = input.length();
        if(length<MIN_NAME_LENGTH || length>MAX_NAME_LENGTH){
            return "Name should be between "+MIN_NAME_LENGTH+" and "+MAX_NAME_LENGTH +" characters";
        }
        return null;
    }

    String validateEmail(String input) {
        if(input == null || input.trim().isEmpty()){
            return "Email cannot be empty";
        }
        String email = input.trim();
        if (!EMAIL_PATTERN.matcher(email).matches()){
            return "Enter a valid email address";
        }
        if(ThiranXDB.getInstance().getEmployeeByEmail(email)!=null){
            return "This Email is registered";
        }
        return null;
    }

    String validatePassword(String input) {
        if(input == null || input.trim().isEmpty()){
            return "password cannot be empty";
        }
        String password = input.trim();
        if(!PASSWORD_PATTERN.matcher(password).matches()){
            return "Password must be at least 8 characters and contain letters and numbers";
        }

        return null;
    }

    String validateConfirmPassword(String password, String confirmPassword) {
        if(confirmPassword==null || confirmPassword.trim().isEmpty() || !confirmPassword.equals(password)){
            return "password do not match";
        }
        return null;
    }

    Long validateDob(String dobText){
        Long dobMillis = ParseHelper.parseDate(dobText);
        if(dobMillis == null) return null;

        if(dobMillis>= System.currentTimeMillis()) return null;
        if(ParseHelper.calculateAgeYears(dobMillis) < MIN_AGE_YEARS) return null;
        return dobMillis;
    }

    String validateMobile(String input) {
        if(input == null || input.trim().isEmpty()){
            return "mobile cannot be empty";
        }
        if(!MOBILE_PATTERN.matcher(input.trim()).matches()){
            return "Enter a valid 10 digit mobile number";
        }
        return null;
    }

    void saveEmployee(String name, String email, String password, String mobile, Long dob, Role role, Long reportingTo) {
         Employee newEmployee = new Employee();
         newEmployee.setName(name.trim());
         newEmployee.setEmailId(email.trim());
         newEmployee.setPassword(password);
         newEmployee.setMobileNo(mobile.trim());
         newEmployee.setDob(dob);
         newEmployee.setRole(role);
         newEmployee.setReportingTo(reportingTo);
         newEmployee.setStatus(EmployeeStatus.ACTIVE);

         Employee saved = ThiranXDB.getInstance().addEmployee(newEmployee);


    }
}
