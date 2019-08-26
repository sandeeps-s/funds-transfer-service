package bank.revolut.fund.transfer.domain.model;


import javax.inject.Inject;
import java.time.LocalDateTime;

public class FundsTransferService {

    private final AccountRepository accountRepository;

    @Inject
    public FundsTransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public FundsTransfer transfer(FundsTransfer fundsTransfer) {

        fundsTransfer.setInitiatedOn(LocalDateTime.now());

        Account fromAccount = accountRepository.retrieveAccount(fundsTransfer.fromAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid account number"));

        Account payeeAccount = accountRepository.retrieveAccount(fundsTransfer.payeeAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid account number"));

        fromAccount.transferTo(payeeAccount, fundsTransfer.amount());

        accountRepository.storeUpdatedAccount(fromAccount);

        accountRepository.storeUpdatedAccount(payeeAccount);

        fundsTransfer.setCompletedOn(LocalDateTime.now());

        return fundsTransfer;
    }
}
