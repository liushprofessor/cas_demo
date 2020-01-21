
package com.cas;


import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

/**
 * 功能： TODO(用一句话描述类的功能)
 *
 * ──────────────────────────────────────────
 *   version  变更日期    修改人    修改说明
 * ------------------------------------------
 *   V1.0.0   2020/1/13    Liush     初版
 * ──────────────────────────────────────────
 */

public class Config implements AuthenticationEventExecutionPlanConfigurer {


    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @Autowired
    private UserMapper userMapper;



    @Bean
    @DependsOn({"dataSource","mapperScannerConfigurer","sqlSessionFactory"})
    public AuthenticationHandler authenticationHandler(){

        return new CustomUsernamePasswordAuthentication(CustomUsernamePasswordAuthentication.class.getName(), servicesManager, new DefaultPrincipalFactory(), 1,userMapper);
    }


    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandler(authenticationHandler());
    }




}
