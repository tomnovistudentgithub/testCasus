package nl.maastrichtuniversity.myusc.model;

import jakarta.persistence.*;

@Entity
//@DiscriminatorValue("ADMIN")
public class Admin extends User {

    @Column
    private String department;


    public Admin(UserType userType) {
        super(userType);
    }

    public Admin() {
        super(UserType.ADMIN);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
