package bank.revolut.fund.transfer.infrastructure.database;

import bank.revolut.fund.transfer.domain.model.Account;
import bank.revolut.fund.transfer.domain.model.AccountRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class MyBatisAccountRepository implements AccountRepository {

    private final SqlSessionFactory sqlSessionFactory;

    @Inject
    public MyBatisAccountRepository(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Optional<Account> retrieveAccount(String accountNumber) {

        try (SqlSession session = sqlSessionFactory.openSession()) {
            AccountMapper accountMapper = session.getMapper(AccountMapper.class);
            Account account = accountMapper.selectAccountByAccountNumber(accountNumber);
            if (Objects.nonNull(account))
                return Optional.of(account);
            else
                return Optional.empty();
        }
    }

    @Override
    public void storeUpdatedAccount(Account account) {

        try (SqlSession session = sqlSessionFactory.openSession()) {
            AccountMapper accountMapper = session.getMapper(AccountMapper.class);
            accountMapper.updateAccount(account);
        }
    }
}
