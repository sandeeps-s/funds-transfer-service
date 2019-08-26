package bank.revolut.fund.transfer.domain.model;


import java.util.Optional;

public interface AccountRepository {

    Optional<Account> retrieveAccount(String accountNumber);

    void storeUpdatedAccount(Account account);

}
