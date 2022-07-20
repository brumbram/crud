package co.zip.candidate.userapi.repository;

import co.zip.candidate.userapi.model.entity.Account;
import co.zip.candidate.userapi.model.entity.User;
import co.zip.candidate.userapi.model.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByUserId(Long Id);

    Account findByAccountType(AccountType accountType);
}
