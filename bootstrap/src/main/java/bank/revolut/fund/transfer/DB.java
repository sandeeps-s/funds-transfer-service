package bank.revolut.fund.transfer;

import bank.revolut.fund.transfer.infrastructure.database.liquibase.LiquibaseUtil;

public class DB {

    private DB() {

    }

    public static void initialize() {

        LiquibaseUtil.updateDB(
                Config.instance.getPropertyAsString("app.db.url"),
                Config.instance.getPropertyAsString("app.db.username"),
                Config.instance.getPropertyAsString("app.db.password"),
                Config.instance.getPropertyAsString("app.db.changelog")
        );
    }

}
