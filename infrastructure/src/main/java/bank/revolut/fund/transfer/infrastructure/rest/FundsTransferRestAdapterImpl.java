package bank.revolut.fund.transfer.infrastructure.rest;

import bank.revolut.fund.transfer.application.PaymentService;
import bank.revolut.fund.transfer.domain.model.FundsTransfer;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class FundsTransferRestAdapterImpl implements FundsTransferRestAdapter {

    @Inject
    private PaymentService paymentService;

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public FundsTransferRestAdapterImpl() {
    }

    public FundsTransferRestAdapterImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public Response transferFunds(FundsTransferRepresentation fundsTransferRepresentation) {

        FundsTransfer fundsTransferResult = paymentService.transferFunds(fundsTransferRepresentation.toFundsTransferRequest());
        return Response.status(Response.Status.CREATED).entity(FundsTransferRepresentation.from(fundsTransferResult)).build();

    }

}
