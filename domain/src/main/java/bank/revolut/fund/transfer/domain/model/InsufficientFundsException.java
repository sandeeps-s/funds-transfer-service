package bank.revolut.fund.transfer.domain.model;

import org.javamoney.moneta.Money;

public class InsufficientFundsException extends RuntimeException {

    private final Money balance;
    private final Money requestedAmount;

    public InsufficientFundsException(Money balance, Money requestedAmount) {
        this.balance = balance;
        this.requestedAmount = requestedAmount;
    }

    public InsufficientFundsException(String message, Money balance, Money requestedAmount) {
        super(message);
        this.balance = balance;
        this.requestedAmount = requestedAmount;
    }

    public Money balance() {
        return balance;
    }

    public Money requestedAmount() {
        return requestedAmount;
    }
}
