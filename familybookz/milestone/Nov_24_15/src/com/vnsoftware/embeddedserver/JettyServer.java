package com.vnsoftware.embeddedserver;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.deploy.DeploymentManager;
import org.eclipse.jetty.deploy.providers.WebAppProvider;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

import com.vnsoftware.util.ConfigurationHelper;

public class JettyServer {
	public JettyServer() throws Exception {
		PropertyConfigurator.configure(ConfigurationHelper.getInstance().getValue("log4j"));
        Resource fileserver_xml = Resource.newSystemResource("jetty.xml");
        XmlConfiguration configuration = new XmlConfiguration(fileserver_xml.getInputStream());
        Server server = (Server)configuration.configure();
        WebAppProvider p = new WebAppProvider();
        p.setMonitoredDirName("webapps");
        p.setDefaultsDescriptor("webapps/WEB-INF/web.xml");
//        p.setScanInterval(1);
        server.getBean(DeploymentManager.class).addAppProvider(p);
        
        WebAppContext context = new WebAppContext();
        context.setResourceBase("webapps");
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        server.setHandler(context);
        //Start worker
        new CoreWorker().start();
        
		server.start();
		server.join();
		System.out.println("Server running...");
	}
	public static void main(String[] args) throws Exception {
		new JettyServer();
	}
}
