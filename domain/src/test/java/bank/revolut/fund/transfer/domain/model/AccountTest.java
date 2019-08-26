package bank.revolut.fund.transfer.domain.model;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AccountTest {

    private final String mockAccountNumber = "REV007";
    private final Money mockStartingBalance = Money.of(1000, "EUR");

    private Account account;

    @BeforeEach
    void setUp() {

        account = new Account(1, mockAccountNumber, mockStartingBalance, LocalDateTime.now(), null, 1);
    }


    @Test
    void shouldFailWithException_givenBlankAccountNumber_when_creatingAccount() {

        Throwable thrown = catchThrowable(() -> account = new Account(1, "", mockStartingBalance, LocalDateTime.now(), null, 1));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid account number = ");
    }

    @Test
    void shouldFailWithException_givenInvalidAccountNumber_when_creatingAccount() {
        Throwable thrown = catchThrowable(() -> account = new Account(1, "REV_007", mockStartingBalance, LocalDateTime.now(), null, 1));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid account number = REV_007");

    }

    @Test
    void shouldGetCorrectAccountNumber() {

        String actualAccountNumber = account.accountNumber();

        assertThat(actualAccountNumber).isEqualTo(mockAccountNumber);
    }

    @Test
    void shouldGetCorrectBalance() {

        Money actualBalance = account.balance();

        assertThat(actualBalance).isEqualTo(mockStartingBalance);
    }

    @Test
    void shouldUpdateAccountsWithCorrectAmounts_when_transferringFunds() {

        //given
        Money transferAmount = Money.of(100, Monetary.getCurrency("EUR"));
        Account payeeAccount = new Account(1, "REV008", Money.of(1000, Monetary.getCurrency("EUR")), LocalDateTime.now(), null, 1);

        //when
        account.transferTo(payeeAccount, transferAmount);

        //then
        Money expectedFromAccountBalance = Money.of(900, Monetary.getCurrency("EUR"));
        Money expectedPayeeAccountBalance = Money.of(1100, Monetary.getCurrency("EUR"));
        assertThat(account.balance()).isEqualTo(expectedFromAccountBalance);
        assertThat(payeeAccount.balance()).isEqualTo(expectedPayeeAccountBalance);
    }

    @Test
    void shouldFailWithException_given_insufficientFundsInFromAccount_when_transferringFunds() {

        //given
        Money transferAmount = Money.of(1100, Monetary.getCurrency("EUR"));
        Account payeeAccount = new Account(1, "REV008", Money.of(1000, Monetary.getCurrency("EUR")), LocalDateTime.now(), null, 1);

        //when
        Throwable thrown = catchThrowable(() -> account.transferTo(payeeAccount, transferAmount));

        //then
        assertThat(thrown).isInstanceOf(InsufficientFundsException.class).hasMessage("Insufficient Funds");
        assertThat(((InsufficientFundsException) thrown).balance()).isEqualTo(Money.of(1000, Monetary.getCurrency("EUR")));
        assertThat(((InsufficientFundsException) thrown).requestedAmount()).isEqualTo(Money.of(1100, Monetary.getCurrency("EUR")));
    }

    @Test
    void shouldUpdateBalanceCorrectly_when_creditingAmount() {
        //given
        Money creditAmount = Money.of(100, Monetary.getCurrency("EUR"));

        account.credit(creditAmount);
        Money expectedBalance = Money.of(1100, Monetary.getCurrency("EUR"));
        assertThat(account.balance()).isEqualTo(expectedBalance);
    }

    @Test
    void shouldUpdateBalanceCorrectly_when_debitingAmount() {
        //given
        Money debitAmount = Money.of(100, Monetary.getCurrency("EUR"));

        account.debit(debitAmount);
        Money expectedBalance = Money.of(900, Monetary.getCurrency("EUR"));
        assertThat(account.balance()).isEqualTo(expectedBalance);
    }

    @Test
    void debit() {
    }
}