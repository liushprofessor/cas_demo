
package com.cas;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * 功能： TODO(用一句话描述类的功能)
 *
 * ──────────────────────────────────────────
 *   version  变更日期    修改人    修改说明
 * ------------------------------------------
 *   V1.0.0   2020/1/19    Liush     初版
 * ──────────────────────────────────────────
 */

@EnableConfigurationProperties({CasConfigurationProperties.class})
public class CASWebFlowConfig implements CasWebflowExecutionPlanConfigurer{

    @Autowired
    private FlowBuilderServices flowBuilderServices;

    @Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowRegistry;

    @Autowired
    @Qualifier("logoutFlowRegistry")
    FlowDefinitionRegistry logoutFlowRegistry;


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    private ServicesManager servicesManager;


    @Bean
    @Order(-1)
    public CasWebflowConfigurer defaultWebflowConfigurer() {
        MyLoginWebflowConfiger c = new MyLoginWebflowConfiger(flowBuilderServices,loginFlowRegistry , this.applicationContext, this.casProperties);
        c.setLogoutFlowDefinitionRegistry(logoutFlowRegistry);
        return c;
    }






}
