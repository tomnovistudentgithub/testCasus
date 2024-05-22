package nl.maastrichtuniversity.myusc.model;

public class PriceRequestDto {

    private Long userId;
    private UserType userType;
    private MembershipType membershipType;
    private int enrollmentMonth;

    public Long getUserId() {
        return userId;
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

    public int getEnrollmentMonth() {
        return enrollmentMonth;
    }

    public void setEnrollmentMonth(int enrollmentMonth) {
        this.enrollmentMonth = enrollmentMonth;
    }
}
