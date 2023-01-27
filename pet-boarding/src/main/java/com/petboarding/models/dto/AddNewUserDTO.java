package com.petboarding.models.dto;

import com.petboarding.models.Role;

public class AddNewUserDTO extends RegisterFormDTO{
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
