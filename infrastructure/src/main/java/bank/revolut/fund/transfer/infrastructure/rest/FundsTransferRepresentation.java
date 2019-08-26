package bank.revolut.fund.transfer.infrastructure.rest;

import bank.revolut.fund.transfer.domain.model.FundsTransfer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FundsTransferRepresentation {

    private static final String ACCOUNT_NUMBER_PATTERN = "^[A-Z]{1,3}[0-9]{1,3}$";

    @Pattern(regexp = ACCOUNT_NUMBER_PATTERN)
    private String fromAccountNumber;
    @Pattern(regexp = ACCOUNT_NUMBER_PATTERN)
    private String payeeAccountNumber;
    @Valid
    @NotNull
    private Amount amount;
    private LocalDateTime initiatedOn;
    private LocalDateTime completedOn;

    public FundsTransferRepresentation() {
    }

    public FundsTransferRepresentation(String fromAccountNumber, String payeeAccountNumber, Amount amount) {
        this.fromAccountNumber = fromAccountNumber;
        this.payeeAccountNumber = payeeAccountNumber;
        this.amount = amount;
    }

    FundsTransfer toFundsTransferRequest() {
        return FundsTransfer.of(fromAccountNumber, payeeAccountNumber, amount.toMoney());
    }

    static FundsTransferRepresentation from(FundsTransfer fundsTransfer) {
        FundsTransferRepresentation fundsTransferRepresentation = new FundsTransferRepresentation(fundsTransfer.fromAccountNumber(), fundsTransfer.payeeAccountNumber(), Amount.from(fundsTransfer.amount()));
        fundsTransferRepresentation.setInitiatedOn(fundsTransfer.initiatedOn());
        fundsTransferRepresentation.setCompletedOn(fundsTransfer.completedOn());
        return fundsTransferRepresentation;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(String payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getInitiatedOn() {
        return initiatedOn;
    }

    public void setInitiatedOn(LocalDateTime initiatedOn) {
        this.initiatedOn = initiatedOn;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(LocalDateTime completedOn) {
        this.completedOn = completedOn;
    }
}
