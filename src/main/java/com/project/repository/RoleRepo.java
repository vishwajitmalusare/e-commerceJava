package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Role;

@Repository
public interface RoleRepo  extends JpaRepository<Role, Integer>{

}
