package az.unitech.dao.repository;

import az.unitech.dao.entity.Account;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, String> {


    Optional<List<Account>> findByUserIdAndStatus(String userId, String status);

    @Query("SELECT a FROM Account a WHERE a.accountNumber = FUNCTION('uuid', ?1)")
    Optional<Account> findByAccountNumber(String accountNumber);
}
