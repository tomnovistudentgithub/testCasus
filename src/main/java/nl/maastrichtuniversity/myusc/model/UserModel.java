package nl.maastrichtuniversity.myusc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserModel {
    private Long id = -1L;
    private String userName;
    private String password;
    private List<RoleModel> roles = new ArrayList<>();
    private boolean isExpired;
    private boolean isLocked;
    private boolean areCredentialsExpired;
    private boolean isEnabled;
    private String firstName;
    private String lastName;
    private String email;
    private int enrollmentYear;
    private Integer enrollmentMonth;
    private List<Event> events = new ArrayList<>();
    private List<Membership> memberships = new ArrayList<>();
    private UserType userType;
    public UserModel() {
    }

    public UserModel(Long id) {
        this.id = id;
    }

    // Getters and Setters for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleModel> roles) {
        this.roles = roles;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean areCredentialsExpired() {
        return areCredentialsExpired;
    }

    public void areCredentialsExpired(boolean areCredentialsExpired) {
        this.areCredentialsExpired = areCredentialsExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public Integer getEnrollmentMonth() {
        return enrollmentMonth;
    }

    public void setEnrollmentMonth(Integer enrollmentMonth) {
        this.enrollmentMonth = enrollmentMonth;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<String> getRoleNames() {
        return roles.stream()
                .map(RoleModel::getRoleName)
                .collect(Collectors.toList());
    }
}

