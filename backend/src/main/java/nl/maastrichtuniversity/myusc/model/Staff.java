package nl.maastrichtuniversity.myusc.model;

import jakarta.persistence.*;
import nl.maastrichtuniversity.myusc.entities.User;

@Entity
//@DiscriminatorValue("STAFF")
public class Staff extends User {

    private String department;

    private String position;

    private String employeeType;


    @Enumerated(EnumType.STRING)
    private UserType userType;

//    public Staff(UserType userType) {
//        super(userType);
//    }
//
//    public Staff() {
//        super(UserType.STAFF);
//    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
