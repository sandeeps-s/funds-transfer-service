package bank.revolut.fund.transfer.application;

import bank.revolut.fund.transfer.domain.model.FundsTransfer;
import bank.revolut.fund.transfer.domain.model.FundsTransferService;
import org.mybatis.cdi.Transactional;

import javax.inject.Inject;

public class PaymentService {

    private final FundsTransferService fundsTransferService;

    @Inject
    public PaymentService(FundsTransferService fundsTransferService) {
        this.fundsTransferService = fundsTransferService;
    }

    @Transactional
    public FundsTransfer transferFunds(FundsTransfer fundsTransfer){
        return fundsTransferService.transfer(fundsTransfer);
    }
}
