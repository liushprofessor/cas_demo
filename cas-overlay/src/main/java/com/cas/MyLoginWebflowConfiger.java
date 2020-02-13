
package com.cas;

import org.apereo.cas.authentication.RememberMeUsernamePasswordCredential;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.configurer.DefaultLoginWebflowConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.BinderConfiguration;
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
public class MyLoginWebflowConfiger extends DefaultLoginWebflowConfigurer {


    public MyLoginWebflowConfiger(FlowBuilderServices flowBuilderServices, FlowDefinitionRegistry flowDefinitionRegistry, ApplicationContext applicationContext, CasConfigurationProperties casProperties) {
        super(flowBuilderServices, flowDefinitionRegistry, applicationContext, casProperties);
    }

    @Override
    protected void createRememberMeAuthnWebflowConfig(Flow flow){

        if (this.casProperties.getTicket().getTgt().getRememberMe().isEnabled()) {
            this.createFlowVariable(flow, "credential", RememberMeUsernamePasswordCredential.class);
            ViewState state = (ViewState)this.getState(flow, "viewLoginForm", ViewState.class);
            BinderConfiguration cfg = this.getViewStateBinderConfiguration(state);
            cfg.addBinding(new BinderConfiguration.Binding("rememberMe", (String)null, false));
        } else {
            this.createFlowVariable(flow, "credential", MyUsernamePasswordCredential.class);
        }

    }



}
