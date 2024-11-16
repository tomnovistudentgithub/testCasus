package nl.maastrichtuniversity.myusc.model;


import jakarta.persistence.*;

@Entity
public class Price {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;

    private double price;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
