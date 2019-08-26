package bank.revolut.fund.transfer.domain.model;

import org.javamoney.moneta.Money;

import java.time.LocalDateTime;

public class FundsTransfer implements Transaction {

    private final String fromAccountNumber;
    private final String payeeAccountNumber;
    private final Money amount;
    private LocalDateTime initiatedOn;
    private LocalDateTime completedOn;

    private FundsTransfer(String fromAccountNumber, String payeeAccountNumber, Money amount) {
        this.fromAccountNumber = fromAccountNumber;
        this.payeeAccountNumber = payeeAccountNumber;
        this.amount = amount;
    }

    public static FundsTransfer of(String fromAccountNumber, String payeeAccountNumber, Money transferAmount) {
        return new FundsTransfer(fromAccountNumber, payeeAccountNumber, transferAmount);
    }

    public String fromAccountNumber() {
        return fromAccountNumber;
    }

    public String payeeAccountNumber() {
        return payeeAccountNumber;
    }

    public Money amount() {
        return amount;
    }

    public LocalDateTime initiatedOn() {
        return initiatedOn;
    }

    public LocalDateTime completedOn() {
        return completedOn;
    }

    public void setInitiatedOn(LocalDateTime initiatedOn) {
        this.initiatedOn = initiatedOn;
    }

    public void setCompletedOn(LocalDateTime completedOn) {
        this.completedOn = completedOn;
    }

}
