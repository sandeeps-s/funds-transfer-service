package bank.revolut.fund.transfer.application;

import bank.revolut.fund.transfer.domain.model.Account;
import bank.revolut.fund.transfer.domain.model.AccountRepository;

import javax.inject.Inject;
import java.util.Optional;

public class AccountService {

    private AccountRepository accountRepository;

    @Inject
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> getAccount(String accountNumber) {
        return accountRepository.retrieveAccount(accountNumber);
    }

}
