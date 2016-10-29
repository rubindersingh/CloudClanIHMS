package com.asu.cloudclan.security;

import com.asu.cloudclan.entity.cassandra.Container;
import com.asu.cloudclan.entity.cassandra.User;
import com.asu.cloudclan.service.cassandra.ContainerCoreService;
import com.asu.cloudclan.service.cassandra.UserService;
import com.datastax.driver.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rubinder on 10/26/16.
 */
@Component
public class SpringSecurityAuthenticationService implements UserDetailsService {

    @Autowired
    UserService userService;
    @Autowired
    ContainerCoreService containerCoreService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SpringSecurityUser springSecurityUser = null;
        User user = userService.find(username);
        if(user != null) {
            springSecurityUser = new SpringSecurityUser();
            springSecurityUser.setUsername(user.getEmailId());
            springSecurityUser.setPassword(user.getPassword());

            Role role = new Role();
            role.setName("ROLE_USER");
            List<Role> roles = new ArrayList<>(1);
            roles.add(role);
            springSecurityUser.setAuthorities(roles);
        }
        return springSecurityUser;
    }

    public boolean isContainerAccessible(Principal principal, String containerId) {
        return containerCoreService.isPublic(containerId) || containerCoreService.hasAccess(principal.getName(), containerId);
    }
}

class SpringSecurityUser implements UserDetails {

    private String username;
    private String password;
    private List<Role> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

class Role implements GrantedAuthority{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
