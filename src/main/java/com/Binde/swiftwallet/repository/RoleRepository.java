package com.Binde.swiftwallet.repository;

import com.Binde.swiftwallet.constants.RoleEnum;
import com.Binde.swiftwallet.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

Role findRoleByName(RoleEnum roleEnum);
}
