package bank.revolut.fund.transfer;

import bank.revolut.fund.transfer.infrastructure.rest.AccountRepresentation;
import bank.revolut.fund.transfer.infrastructure.rest.Amount;
import bank.revolut.fund.transfer.infrastructure.rest.FundsTransferRepresentation;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class FundsTransferComponentTest {

    private static final int SERVER_PORT = 8090;

    private static final String SERVICE_BASE_URI = String.format("http://localhost:%d/api", SERVER_PORT);

    @BeforeEach
    void setUp() throws ServletException {

        DB.initialize();
        UndertowServer.startContainer(8090);
    }

    @AfterEach
    void tearDown() {
        UndertowServer.stopContainer();
    }

    @Test
    public void shouldUpdateAccountsWithCorrectAmounts_when_transferringFunds() {

        //given
        String fromAccountNumber = "REV007";
        String payeeAccountNumber = "REV008";
        Amount transferAmount = new Amount(new BigDecimal(100), "EUR");
        FundsTransferRepresentation fundsTransferRepresentation =
                new FundsTransferRepresentation(fromAccountNumber, payeeAccountNumber, transferAmount);

        //when
        Response fundsTransferResponse = ResteasyClientBuilder.newClient()
                .target(SERVICE_BASE_URI).path("fund-transfers")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(fundsTransferRepresentation, MediaType.APPLICATION_JSON_TYPE));

        //then
        assertThat(201).isEqualTo(fundsTransferResponse.getStatus());

        Response fromAccountResponse = ResteasyClientBuilder.newClient()
                .target(SERVICE_BASE_URI).path("accounts").path(fromAccountNumber)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        assertThat(200).isEqualTo(fromAccountResponse.getStatus());
        AccountRepresentation fromAccountRepresentation = fromAccountResponse.readEntity(AccountRepresentation.class);
        assertThat(new BigDecimal(9900)).isEqualTo(fromAccountRepresentation.getBalance().getValue());

        Response payeeAccountResponse = ResteasyClientBuilder.newClient()
                .target(SERVICE_BASE_URI).path("accounts").path(payeeAccountNumber)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        assertThat(200).isEqualTo(payeeAccountResponse.getStatus());
        AccountRepresentation payeeAccountRepresentation = payeeAccountResponse.readEntity(AccountRepresentation.class);
        assertThat(new BigDecimal(10100)).isEqualTo(payeeAccountRepresentation.getBalance().getValue());

    }

    @Test
    public void shouldFailWithBadRequest_given_invalidTransferDetails_when_transferringFunds() {

        //given
        String fromAccountNumber = "";
        String payeeAccountNumber = "REV008";
        Amount transferAmount = new Amount(new BigDecimal(100), "EUR");
        FundsTransferRepresentation fundsTransferRepresentation =
                new FundsTransferRepresentation(fromAccountNumber, payeeAccountNumber, transferAmount);

        //when
        Response fundsTransferResponse = ResteasyClientBuilder.newClient()
                .target(SERVICE_BASE_URI).path("fund-transfers")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(fundsTransferRepresentation, MediaType.APPLICATION_JSON_TYPE));

        //then
        assertThat(400).isEqualTo(fundsTransferResponse.getStatus());
    }

}
