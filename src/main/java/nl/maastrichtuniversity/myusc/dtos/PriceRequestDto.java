package nl.maastrichtuniversity.myusc.dtos;

import nl.maastrichtuniversity.myusc.model.MembershipType;
import nl.maastrichtuniversity.myusc.model.UserType;

public class PriceRequestDto {

    private Long userId;
    private UserType userType;
    private MembershipType membershipType;


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

}
