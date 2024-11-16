package nl.maastrichtuniversity.myusc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import nl.maastrichtuniversity.myusc.entities.User;

import java.time.LocalDate;

@Entity
public class Membership {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column
    private double price;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate expirationDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}