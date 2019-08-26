package bank.revolut.fund.transfer.infrastructure.rest;

import bank.revolut.fund.transfer.domain.model.Account;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class AccountRepresentation {

    private long id;

    private String accountNumber;

    private Amount balance;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    private int version;

    public static AccountRepresentation from(Account account) {
        AccountRepresentation accountRepresentation = new AccountRepresentation();
        accountRepresentation.setId(account.id());
        accountRepresentation.setAccountNumber(account.accountNumber());
        accountRepresentation.setBalance(Amount.from(account.balance()));
        accountRepresentation.setCreatedOn(account.createdOn());
        accountRepresentation.setModifiedOn(account.modifiedOn());
        accountRepresentation.setVersion(account.version());
        return accountRepresentation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
