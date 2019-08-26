package bank.revolut.fund.transfer.infrastructure.database.liquibase;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

public class LiquibaseUtil {

    private LiquibaseUtil(){

    }

    public static void updateDB(String dbUrl, String dbUsername, String dbPassword, String dbChangelog) {

        try {
            ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(LiquibaseUtil.class.getClassLoader());
            Database database = DatabaseFactory.getInstance()
                    .openDatabase(
                            dbUrl,
                            dbUsername,
                            dbPassword,
                            null,
                            resourceAccessor
                    );
            Liquibase liquibase = new Liquibase(dbChangelog, resourceAccessor, database);
            liquibase.update(new Contexts());

        } catch (LiquibaseException e) {
            throw new RuntimeException("Error while initializing database", e);
        }
    }

}
