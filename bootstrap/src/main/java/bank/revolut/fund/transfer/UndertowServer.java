package bank.revolut.fund.transfer;

import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

public class UndertowServer {

    private static UndertowJaxrsServer server;

    private UndertowServer() {

    }

    public static void stopContainer() {
        server.stop();
    }

    public static void startContainer(int port) {
        System.setProperty("org.jboss.resteasy.port", String.valueOf(port));
        server = new UndertowJaxrsServer().start();

        ResteasyDeployment deployment = new ResteasyDeploymentImpl();
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");
        deployment.setApplicationClass(FundsTransferApplication.class.getName());
        DeploymentInfo di = server.undertowDeployment(deployment);
        di.setClassLoader(UndertowServer.class.getClassLoader());
        di.setResourceManager(new ClassPathResourceManager(UndertowServer.class.getClassLoader()));
        di.setContextPath("/api");
        di.setDeploymentName("DI");
        di.addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));
        server.deploy(di);

    }


}
