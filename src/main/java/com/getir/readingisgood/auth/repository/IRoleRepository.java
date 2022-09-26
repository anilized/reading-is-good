package com.getir.readingisgood.auth.repository;

import com.getir.readingisgood.auth.entity.ERole;
import com.getir.readingisgood.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
