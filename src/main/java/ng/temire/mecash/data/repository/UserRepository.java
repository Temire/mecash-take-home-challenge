package ng.temire.mecash.data.repository;

import ng.temire.mecash.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsernameIgnoreCase(@NonNull String username);
}