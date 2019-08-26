package bank.revolut.fund.transfer.infrastructure.rest;

import bank.revolut.fund.transfer.domain.model.Account;
import io.swagger.annotations.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Api(value = "accounts")
@SwaggerDefinition(tags = {@Tag(name = "vehicles", description = "Service to get accounts")})
@Path("/accounts")
public interface AccountRestAdapter {

    @GET
    @Path("/{accountNumber}")
    @ApiOperation(value = "Get Account", notes = "Reads account specified by its accountNumber", response = Account.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Account.class),
            @ApiResponse(code = 500, message = "Internal Error"),
            @ApiResponse(code = 404, message = "not found")
    })
    @Produces("application/json")
    AccountRepresentation getAccount(@Valid
                                     @Pattern(regexp = Account.ACCOUNT_NUMBER_PATTERN)
                                     @PathParam("accountNumber") String accountNumber);

}
