package nl.maastrichtuniversity.myusc.dtos;

import nl.maastrichtuniversity.myusc.model.MembershipType;

import java.time.LocalDate;

public class MembershipDto {
    private Long userId;
    private MembershipType membershipType;
    private int enrollmentYear;
    private int enrollmentMonth;
    private LocalDate startDate;
    private LocalDate expirationDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public int getEnrollmentMonth() {
        return enrollmentMonth;
    }

    public void setEnrollmentMonth(int enrollmentMonth) {
        this.enrollmentMonth = enrollmentMonth;
    }

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
}
