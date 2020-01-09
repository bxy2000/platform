package com.boxy.platform.service.dto;

import com.boxy.platform.domain.Role;
import com.boxy.platform.domain.UserExtends;
import com.boxy.platform.domain.enumeration.Gender;

import java.util.Set;
import java.util.stream.Collectors;

public class UserExtendsDTO {
    private Long id;
    private String username;
    private Gender gender;
    private String mobile;
    private Set<String> roles;

    public UserExtendsDTO(UserExtends userExtends) {
        this.id = userExtends.getId();
        this.username = userExtends.getUsername();
        this.gender = userExtends.getGender();
        this.mobile = userExtends.getMobile();
        this.roles = userExtends.getRoles().stream()
            .map(Role::getRoleName)
            .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
