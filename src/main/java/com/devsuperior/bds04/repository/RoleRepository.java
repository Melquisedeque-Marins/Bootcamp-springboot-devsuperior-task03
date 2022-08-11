package com.devsuperior.bds04.repository;

import com.devsuperior.bds04.entities.Role;
import com.devsuperior.bds04.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    User findByEmail(String email);

}
