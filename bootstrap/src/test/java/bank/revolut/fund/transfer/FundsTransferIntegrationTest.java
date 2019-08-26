package bank.revolut.fund.transfer;

import bank.revolut.fund.transfer.application.PaymentService;
import bank.revolut.fund.transfer.domain.model.AccountRepository;
import bank.revolut.fund.transfer.domain.model.FundsTransferService;
import bank.revolut.fund.transfer.infrastructure.database.MyBatisAccountRepository;
import bank.revolut.fund.transfer.infrastructure.database.TestSqlSessionFactoryProducer;
import bank.revolut.fund.transfer.infrastructure.rest.Amount;
import bank.revolut.fund.transfer.infrastructure.rest.FundsTransferRepresentation;
import bank.revolut.fund.transfer.infrastructure.rest.FundsTransferRestAdapter;
import bank.revolut.fund.transfer.infrastructure.rest.FundsTransferRestAdapterImpl;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class FundsTransferIntegrationTest {


    private final AccountRepository accountRepository = new MyBatisAccountRepository(TestSqlSessionFactoryProducer.sqlSessionFactory());

    private final FundsTransferService fundsTransferService = new FundsTransferService(accountRepository);

    private final PaymentService paymentService = new PaymentService(fundsTransferService);

    private final FundsTransferRestAdapter fundsTransferRestAdapter = new FundsTransferRestAdapterImpl(paymentService);

    @BeforeEach
    void setUp() {

        DB.initialize();
    }

    @Test
    public void shouldUpdateAccountsWithCorrectAmounts_when_transferringFunds() {

        Amount transferAmount = new Amount(new BigDecimal(100), "EUR");
        FundsTransferRepresentation fundsTransferRequestRep = new FundsTransferRepresentation("REV005", "REV006", transferAmount);

        //when
        fundsTransferRestAdapter.transferFunds(fundsTransferRequestRep);

        //then
        Money expectedFromAccountBalance = Money.of(9900, Monetary.getCurrency("EUR"));
        Money expectedPayeeAccountBalance = Money.of(10100, Monetary.getCurrency("EUR"));
        assertThat(accountRepository.retrieveAccount("REV005").get().balance()).isEqualTo(expectedFromAccountBalance);
        assertThat(accountRepository.retrieveAccount("REV006").get().balance()).isEqualTo(expectedPayeeAccountBalance);
    }

}
