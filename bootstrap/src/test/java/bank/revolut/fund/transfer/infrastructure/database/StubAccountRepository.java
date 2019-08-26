package bank.revolut.fund.transfer.infrastructure.database;

import bank.revolut.fund.transfer.domain.model.Account;
import bank.revolut.fund.transfer.domain.model.AccountRepository;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StubAccountRepository implements AccountRepository {

    private final Map<String, Account> fromAccountNumber;

    public StubAccountRepository(List<Account> accounts) {

        fromAccountNumber = new HashMap<>(Map.of(
                "REV007", new Account(1, "REV007", Money.of(1000, "EUR"), LocalDateTime.now(), null, 1),
                "REV007", new Account(2, "REV008", Money.of(2000, "EUR"), LocalDateTime.now(), null, 1)
        ));
    }

    @Override
    public Optional<Account> retrieveAccount(String accountNumber) {

        return fromAccountNumber.containsKey(accountNumber)
                ? Optional.of(fromAccountNumber.get(accountNumber))
                : Optional.empty();
    }

    @Override
    public void storeUpdatedAccount(Account updatedAccount) {
        if (fromAccountNumber.containsKey(updatedAccount.accountNumber())) {
            fromAccountNumber.replace(updatedAccount.accountNumber(), updatedAccount);
        }
    }
}
