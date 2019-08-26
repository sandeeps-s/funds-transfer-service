package bank.revolut.fund.transfer;

import bank.revolut.fund.transfer.infrastructure.rest.AccountRestAdapterImpl;
import bank.revolut.fund.transfer.infrastructure.rest.FundsTransferRestAdapterImpl;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class FundsTransferApplication extends Application {

    static final int SERVER_PORT = 8080;

    public static void main(String[] args) throws Exception {

        DB.initialize();
        UndertowServer.startContainer(SERVER_PORT);
        System.in.read();
        UndertowServer.stopContainer();
    }

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(FundsTransferRestAdapterImpl.class);
        classes.add(AccountRestAdapterImpl.class);
        return classes;
    }


}
