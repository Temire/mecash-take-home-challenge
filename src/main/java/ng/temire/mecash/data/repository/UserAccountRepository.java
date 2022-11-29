package ng.temire.mecash.data.repository;

import ng.temire.mecash.data.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    boolean existsByNumber(String number);
    Optional<UserAccount> findByNumber(String number);
}