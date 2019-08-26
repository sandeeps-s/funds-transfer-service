package bank.revolut.fund.transfer.infrastructure.rest;

import io.swagger.annotations.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Api(value = "fund-transfers")
@SwaggerDefinition(tags = {@Tag(name = "fund-transfers", description = "API to transfer funds from one account to another account")})
@Path("/fund-transfers")
public interface FundsTransferRestAdapter {

    @ApiOperation(value = "Fund transfer request", notes = "Transfers funds from one account to another account", response = FundsTransferRepresentation.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "OK", response = FundsTransferRepresentation.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    Response transferFunds(@Valid FundsTransferRepresentation fundsTransferRepresentation);

}
