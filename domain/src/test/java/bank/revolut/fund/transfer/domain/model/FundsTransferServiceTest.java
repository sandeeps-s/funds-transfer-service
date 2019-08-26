package bank.revolut.fund.transfer.domain.model;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

;

class FundsTransferServiceTest {

    private final AccountRepository mockAccountRepository = mock(AccountRepository.class);
    private FundsTransferService fundsTransferService;

    private final String fromAccountNumber = "REV007";
    private final String payeeAccountNumber = "REV008";
    private Account stubFromAccount;
    private Account stubPayeeAccount;

    @BeforeEach
    void setUp() {

        fundsTransferService = new FundsTransferService(mockAccountRepository);
        stubFromAccount = new Account(1, fromAccountNumber, Money.of(1000, Monetary.getCurrency("EUR")), LocalDateTime.now(), null, 1);
        stubPayeeAccount = new Account(1, payeeAccountNumber, Money.of(1000, Monetary.getCurrency("EUR")), LocalDateTime.now(), null, 1);
    }

    @Test
    void shouldUpdateAccountsWithCorrectAmounts_when_transferringFunds() {

        //given
        when(mockAccountRepository.retrieveAccount(fromAccountNumber)).thenReturn(Optional.of(stubFromAccount));
        when(mockAccountRepository.retrieveAccount(payeeAccountNumber)).thenReturn(Optional.of(stubPayeeAccount));

        Money transferAmount = Money.of(100, Monetary.getCurrency("EUR"));
        FundsTransfer fundsTransfer = FundsTransfer.of(fromAccountNumber, payeeAccountNumber, transferAmount);

        //when
        FundsTransfer fundsTransferResult = fundsTransferService.transfer(fundsTransfer);

        //then
        Money expectedFromAccountBalance = Money.of(900, Monetary.getCurrency("EUR"));
        Money expectedPayeeAccountBalance = Money.of(1100, Monetary.getCurrency("EUR"));
        assertThat(stubFromAccount.balance()).isEqualTo(expectedFromAccountBalance);
        assertThat(stubPayeeAccount.balance()).isEqualTo(expectedPayeeAccountBalance);
    }

    @Test
    void shouldFailWithException_given_insufficientFundsInFromAccount_when_transferringFunds() {

        //given
        when(mockAccountRepository.retrieveAccount(fromAccountNumber)).thenReturn(Optional.of(stubFromAccount));
        when(mockAccountRepository.retrieveAccount(payeeAccountNumber)).thenReturn(Optional.of(stubPayeeAccount));
        Money transferAmount = Money.of(1100, Monetary.getCurrency("EUR"));
        FundsTransfer fundsTransfer = FundsTransfer.of(fromAccountNumber, payeeAccountNumber, transferAmount);

        //when
        Throwable thrown = catchThrowable(() -> fundsTransferService.transfer(fundsTransfer));

        //then
        assertThat(thrown).isInstanceOf(InsufficientFundsException.class).hasMessage("Insufficient Funds");
        assertThat(((InsufficientFundsException) thrown).balance()).isEqualTo(Money.of(1000, Monetary.getCurrency("EUR")));
        assertThat(((InsufficientFundsException) thrown).requestedAmount()).isEqualTo(Money.of(1100, Monetary.getCurrency("EUR")));
    }

    @Test
    void shouldFailWithException_given_nonExistentFromAccount_when_transferringFunds() {

        //given
        when(mockAccountRepository.retrieveAccount(fromAccountNumber)).thenReturn(Optional.empty());
        when(mockAccountRepository.retrieveAccount(payeeAccountNumber)).thenReturn(Optional.of(stubPayeeAccount));
        Money transferAmount = Money.of(100, Monetary.getCurrency("EUR"));
        FundsTransfer fundsTransfer = FundsTransfer.of(fromAccountNumber, payeeAccountNumber, transferAmount);

        //when
        Throwable thrown = catchThrowable(() -> fundsTransferService.transfer(fundsTransfer));

        //then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessage("Invalid account number");
    }

    @Test
    void shouldFailWithException_given_nonExistentPayeeAccount_when_transferringFunds() {

        //given
        when(mockAccountRepository.retrieveAccount(fromAccountNumber)).thenReturn(Optional.of(stubFromAccount));
        when(mockAccountRepository.retrieveAccount(payeeAccountNumber)).thenReturn(Optional.empty());
        Money transferAmount = Money.of(100, Monetary.getCurrency("EUR"));
        FundsTransfer fundsTransfer = FundsTransfer.of(fromAccountNumber, payeeAccountNumber, transferAmount);

        //when
        Throwable thrown = catchThrowable(() -> fundsTransferService.transfer(fundsTransfer));

        //then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessage("Invalid account number");
    }

}