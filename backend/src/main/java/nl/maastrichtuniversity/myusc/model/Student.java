package nl.maastrichtuniversity.myusc.model;


import jakarta.persistence.*;
import nl.maastrichtuniversity.myusc.entities.User;

@Entity
//@DiscriminatorValue("STUDENT")
public class Student extends User {

    @Column
    private String department;

//    public Student(UserType userType) {
//        super(userType);
//    }
//
//    public Student() {
//        super(UserType.STUDENT);
//    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
