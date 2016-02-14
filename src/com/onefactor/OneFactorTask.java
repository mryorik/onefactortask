package com.onefactor;

import com.onefactor.config.OneFactorTaskSpringConfig;
import com.onefactor.config.SettingsFactory;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * User: Yaroslav Frolikov
 * Date: 10.02.16 23:32
 */
public class OneFactorTask {
    public static void main(String[] args) throws Exception {
        if (!SettingsFactory.INSTANCE.buildSettings(args)) {
            System.exit(-1);
        }

        Server server = new Server(SettingsFactory.INSTANCE.getSettings().getPort());

        AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
        wac.register(OneFactorTaskSpringConfig.class);

        WebAppContext contextHandler = new WebAppContext();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath("/");
        contextHandler.setResourceBase("/");
        contextHandler.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(wac)), "/");
        contextHandler.addEventListener(new ContextLoaderListener(wac));

        server.setHandler(contextHandler);
        server.start();
        server.join();
    }
}
