package ru.maksmerfy.testforinside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.maksmerfy.testforinside.models.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    @Query(value = "SELECT message from messages where user_id = :userId order by date desc limit :limit", nativeQuery = true)
    List<String> findAllByUserOrderByDateDesc(@Param("userId") String userId, @Param("limit") int limit);
}
