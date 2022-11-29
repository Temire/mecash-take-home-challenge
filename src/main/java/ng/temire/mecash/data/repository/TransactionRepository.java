package ng.temire.mecash.data.repository;

import ng.temire.mecash.data.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.sql.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByReference(String reference);
    @Query("select t from Transaction t where t.transactionDate between ?1 and ?2 order by t.transactionDate")
    List<Transaction> findAllByTransactionDateIsBetweenOrderByTransactionDateAsc(Date transactionDateStart, Date transactionDateEnd, Pageable pageable);

    @Query("select t from Transaction t where t.toAccount = ?1 or t.fromAccount = ?1 and t.transactionDate between ?2 and ?3 order by t.transactionDate")
    List<Transaction> findByToAccountIsOrFromAccountAndTransactionDateIsBetweenOrderByTransactionDateAsc(String toAccount, @Nullable Date transactionDateStart, @Nullable Date transactionDateEnd, Pageable pageable);
}