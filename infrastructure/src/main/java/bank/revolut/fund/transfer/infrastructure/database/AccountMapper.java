package bank.revolut.fund.transfer.infrastructure.database;

import bank.revolut.fund.transfer.domain.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

    boolean selectAccountExists(@Param("accountNumber") String accountNumber);

    Account selectAccountByAccountNumber(@Param("accountNumber") String accountNumber);

    void updateAccount(Account account);

}
