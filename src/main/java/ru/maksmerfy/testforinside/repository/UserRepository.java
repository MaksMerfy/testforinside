package ru.maksmerfy.testforinside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maksmerfy.testforinside.models.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
