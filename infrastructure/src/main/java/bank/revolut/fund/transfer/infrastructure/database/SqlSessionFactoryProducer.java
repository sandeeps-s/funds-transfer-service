package bank.revolut.fund.transfer.infrastructure.database;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.cdi.SessionFactoryProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.io.Reader;

public class SqlSessionFactoryProducer {

    private static final String MYBATIS_CONFIG_FILE = "bank/revolut/fund/transfer/infrastructure/database/mybatis-config.xml";

    @Produces
    @ApplicationScoped
    @SessionFactoryProvider
    public SqlSessionFactory produceSqlSessionFactory() {
        try (Reader reader = Resources.getResourceAsReader(MYBATIS_CONFIG_FILE)) {
            return new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new IllegalStateException("Error in SessionFactory producer", e);
        }
    }

}
