package bank.revolut.fund.transfer.infrastructure.rest;

import bank.revolut.fund.transfer.application.AccountService;
import bank.revolut.fund.transfer.domain.model.Account;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

public class AccountRestAdapterImpl implements AccountRestAdapter {

    private AccountService accountService;

    public AccountRestAdapterImpl() {
    }

    @Inject
    public AccountRestAdapterImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountRepresentation getAccount(String accountNumber) {

        Account account = accountService.getAccount(accountNumber)
                .orElseThrow(NotFoundException::new);
        return AccountRepresentation.from(account);
    }

}
