package com.hc.test.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * test server to serve search request
 */
public class TestServer{
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServer.class);
    private static final int port = 9999;

    public static void main(String[] args) throws Exception {
        new TestServer().start();
    }
    public void start() throws Exception {
        Server server = new Server(port);
        String contextPath = this.getClass().getClassLoader().getResource("")
                .getPath() + "webapp/";
        LOGGER.info("get context path {}", contextPath);
        WebAppContext webapp = new WebAppContext() ;
        webapp.getServletContext().getContextHandler().setMaxFormContentSize(2097152);

        webapp.setDescriptor(contextPath + "WEB-INF/web.xml") ;
        webapp.setResourceBase(contextPath) ;
        webapp.setContextPath("/") ;
        webapp.addServlet(new ServletHolder(new HelloServlet()), "/hello");
        webapp.addServlet(new ServletHolder(new TreeNodeServlet()), "/class");
        webapp.addServlet(new ServletHolder(new MethodServlet()), "/method");

        webapp.setDisplayName("test-runner") ;
        webapp.setParentLoaderPriority(true) ;

        server.setHandler(webapp);

        server.start();
        server.join();
    }
}
