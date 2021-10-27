package ru.maksmerfy.testforinside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maksmerfy.testforinside.models.Role;

public interface RoleRepositopry extends JpaRepository<Role, String> {
    Role findByName(String roleName);
}
