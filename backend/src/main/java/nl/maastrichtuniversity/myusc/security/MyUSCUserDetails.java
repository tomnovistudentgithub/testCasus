package nl.maastrichtuniversity.myusc.security;

import nl.maastrichtuniversity.myusc.model.RoleModel;
import nl.maastrichtuniversity.myusc.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUSCUserDetails implements UserDetails {

    private final UserModel user;

    public MyUSCUserDetails(UserModel user) {
        this.user = user;
    }

    public MyUSCUserDetails(String username, List<String> roles) {
        user = new UserModel();
        user.setUserName(username);

        for (String role : roles) {
            user.getRoles().add(new RoleModel(role));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleModel role: user.getRoles()) {
            if(role.isActive()) {
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return  user.getId() + "::" + user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.isExpired();
    }
    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.areCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}



