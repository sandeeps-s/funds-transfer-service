package bank.revolut.fund.transfer.domain.model;

import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Pattern;

public class Account {

    public static final String ACCOUNT_NUMBER_PATTERN = "^[A-Z]{1,3}[0-9]{1,3}$";

    private long id;

    private String accountNumber;

    private Money balance;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    private int version;

    public Account() {
    }

    public Account(
            long id,
            String accountNumber,
            Money balance,
            LocalDateTime createdOn,
            LocalDateTime modifiedOn,
            int version
    ) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
        this.version = version;

        validateAccount();
    }

    public long id() {
        return id;
    }

    public String accountNumber() {
        return accountNumber;
    }

    public Money balance() {
        return balance;
    }

    public LocalDateTime createdOn() {
        return createdOn;
    }

    public LocalDateTime modifiedOn() {
        return modifiedOn;
    }

    public int version() {
        return version;
    }

    public void transferTo(Account payee, Money transferAmount) {

        validateTransferDetails(payee, transferAmount);
        debit(transferAmount);
        payee.credit(transferAmount);
    }

    void credit(Money creditAmount) {
        balance = balance.add(creditAmount);
    }

    void debit(Money debitAmount) {

        if (balance.isLessThan(debitAmount))
            throw new InsufficientFundsException("Insufficient Funds", balance, debitAmount);

        balance = balance.subtract(debitAmount);
        balance.getNumberStripped();
    }

    private void validateAccount() {

        Pattern pattern = Pattern.compile(ACCOUNT_NUMBER_PATTERN);
        if (Objects.isNull(this.accountNumber) || !pattern.matcher(accountNumber).matches())
            throw new IllegalArgumentException(String.format("Invalid account number = %s", accountNumber));

        if (Objects.isNull(this.balance))
            throw new IllegalArgumentException(String.format("Balance cannot be null"));

    }

    private void validateTransferDetails(Account payee, Money transferAmount) {
        if (Objects.isNull(payee))
            throw new IllegalArgumentException(String.format("Cannot transfer to null account"));

        if (Objects.equals(this.accountNumber, payee.accountNumber))
            throw new IllegalArgumentException(String.format("Cannot transfer to same account %s", accountNumber));

        if (Objects.isNull(transferAmount) || transferAmount.isLessThanOrEqualTo(Money.zero(Monetary.getCurrency("EUR"))))
            throw new IllegalArgumentException(String.format("Transfer must be greater than zero"));
    }

}
