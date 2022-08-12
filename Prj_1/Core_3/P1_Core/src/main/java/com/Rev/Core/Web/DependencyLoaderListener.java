package com.Rev.Core.Web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DependencyLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	System.out.println("INIT>");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}