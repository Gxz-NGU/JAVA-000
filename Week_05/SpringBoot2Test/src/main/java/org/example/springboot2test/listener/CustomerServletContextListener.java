/**
 * Project Name:learning-spring
 * File Name:CustomerServletContextListener.java
 * Package Name:cn.richinfo.blockchain.roaming.server.org.example.springboot2test.listener
 * Date:2020年10月14日 13:09
 * Copyright (c) 2020, All Rights Reserved.
 */
package org.example.springboot2test.listener;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;

/**
 * ClassName:CustomerServletContextListener<br/>
 * Function: 自定义的服务器初始化<br/>
 * Date:2020年10月14日 13:09<br/>
 *
 * @author Kai.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@WebListener
public class CustomerServletContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        // 读配置路径，方便以后部署自定义路径
        String ConstantsSDK_CONF_PATH = servletContext.getInitParameter("confPath");
        System.err.println("ConstantsSDK_CONF_PATH= "+ConstantsSDK_CONF_PATH);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("org.example.springboot2test.listener destroyed time=" + new Date());
    }
}