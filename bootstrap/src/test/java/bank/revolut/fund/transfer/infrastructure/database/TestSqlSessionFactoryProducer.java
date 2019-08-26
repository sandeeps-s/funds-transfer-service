package bank.revolut.fund.transfer.infrastructure.database;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public final class TestSqlSessionFactoryProducer {

    private static final String MYBATIS_CONFIG_FILE = "bank/revolut/fund/transfer/infrastructure/database/mybatis-config.xml";

    private static final SqlSessionFactory sqlSessionFactory;

    static {

        try (Reader reader = Resources.getResourceAsReader(MYBATIS_CONFIG_FILE)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new IllegalStateException("Error in SessionFactory producer", e);
        }

    }

    public static SqlSessionFactory sqlSessionFactory() {
        return sqlSessionFactory;
    }

}
