package bank.revolut.fund.transfer;

import bank.revolut.fund.transfer.infrastructure.rest.AccountRepresentation;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountComponentTest {

    private static final String APPLICATION_PATH = "/api";

    private static final String CONTEXT_ROOT = "/";

    private static final int SERVER_PORT = 8090;

    private static final String SERVICE_BASE_URI = String.format("http://localhost:%d/api", SERVER_PORT);

    private UndertowServer server;

    @BeforeEach
    void setUp() throws Exception {

        DB.initialize();
        UndertowServer.startContainer(8090);
    }

    @AfterEach
    void tearDown() {
        UndertowServer.stopContainer();
    }

    @Test
    public void shouldGetCorrectAccountDetails_when_gettingAccount() {

        Response response = ResteasyClientBuilder.newClient()
                .target(SERVICE_BASE_URI).path("accounts").path("REV009")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(200).isEqualTo(response.getStatus());


        AccountRepresentation accountRepresentation = response.readEntity(AccountRepresentation.class);

        assertThat(new BigDecimal(15000)).isEqualTo(accountRepresentation.getBalance().getValue());

    }

    @Test
    public void shouldFailWithBadRequestErrorCode_given_invalidAccountNumber_when_gettingAccount() {

        Response response = ResteasyClientBuilder.newClient()
                .target(SERVICE_BASE_URI).path("accounts").path("REV_009")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(400).isEqualTo(response.getStatus());

    }

}
