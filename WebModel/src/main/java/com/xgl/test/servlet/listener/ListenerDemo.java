package com.xgl.test.servlet.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Component
public class ListenerDemo implements ServletContextListener {

    private final static Logger logger = LoggerFactory.getLogger(ListenerDemo.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("ListenerDemo contextInitialized listener success .......");
        ServletContextListener.super.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("ListenerDemo contextDestroyed listener success .......");
        ServletContextListener.super.contextDestroyed(sce);
    }
}
