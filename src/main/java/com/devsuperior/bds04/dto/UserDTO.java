package com.devsuperior.bds04.dto;

import com.devsuperior.bds04.entities.User;

import javax.persistence.SecondaryTable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {

    private Long id;
    private String email;
    private String password;

    private Set<RoleDTO> rolesDTO = new HashSet<>();

    public UserDTO() {
    }

    public UserDTO(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UserDTO(User user) {
        id = user.getId();
        email = user.getEmail();
        password = user.getPassword();
        rolesDTO = user.getRoles().stream().map(role -> new RoleDTO(role)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDTO> getRolesDTO() {
        return rolesDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(getId(), userDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
