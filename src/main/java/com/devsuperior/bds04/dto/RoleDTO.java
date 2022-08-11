package com.devsuperior.bds04.dto;

import com.devsuperior.bds04.entities.Role;

import java.util.HashSet;
import java.util.Set;

public class RoleDTO {

    private Long id;
    private String authority;

    private Set<ResponseUserDTO> users = new HashSet<>();

    public RoleDTO() {
    }

    public RoleDTO(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

     public RoleDTO(Role role) {
        id = role.getId();
        authority = role.getAuthority();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Set<ResponseUserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<ResponseUserDTO> users) {
        this.users = users;
    }
}
