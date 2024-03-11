package it.uniroma1.commons.repository;

import it.uniroma1.commons.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM users u WHERE u.username=?1",
            nativeQuery = true)
    Collection<User> findFilterAll(String username);
}
