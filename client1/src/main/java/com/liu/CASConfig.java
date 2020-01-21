/**
 * Created on [2020/1/21 13:57] by Administrator
 * <p>
 * 项目名称： cas TODO(项目名称)
 * <p>
 * 本程序版权属于福建慧政通信息科技有限公司所有。
 * 任何组织和个人未经福建慧政通信息科技有限公司许可与授权,不得擅自传播、复制、更改该程序的内容。
 * 本程序受版权法和国际条约的保护。如未经授权而擅自复制或传播本程序(或其中任何部分),
 * 将受到严厉的刑事及民事制裁，并将在法律许可的范围内受到最大可能的起诉!
 * <p>
 * ©2020 福建慧政通信息科技有限公司
 */
package com.liu;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能： TODO(用一句话描述类的功能)
 *
 * ──────────────────────────────────────────
 *   version  变更日期    修改人    修改说明
 * ------------------------------------------
 *   V1.0.0   2020/1/21    Liush     初版
 * ──────────────────────────────────────────
 */
@Configuration
public class CASConfig {

    /*************************************   SSO配置-开始   ************************************************/

    /**
     * SingleSignOutFilter 登出过滤器
     * 该过滤器用于实现单点登出功能，可选配置
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterSingleRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SingleSignOutFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap();
        initParameters.put("casServerUrlPrefix", URLConfig.CAS_SERVER_LOGIN_PATH);
        registration.setInitParameters(initParameters);
        // 设定加载的顺序
        registration.setOrder(1);
        return registration;
    }

    /**
     * SingleSignOutHttpSessionListener 添加监听器
     * 用于单点退出，该过滤器用于实现单点登出功能，可选配置
     *
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean<EventListener> singleSignOutListenerRegistration() {
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<EventListener>();
        registrationBean.setListener(new SingleSignOutHttpSessionListener());
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /**
     * Cas30ProxyReceivingTicketValidationFilter 验证过滤器
     * 该过滤器负责对Ticket的校验工作，必须启用它
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterValidationRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new Cas30ProxyReceivingTicketValidationFilter());

        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap();
        initParameters.put("casServerUrlPrefix", URLConfig.CAS_SERVER_PATH);
        initParameters.put("serverName", URLConfig.SERVER_NAME);

        // 是否对serviceUrl进行编码，默认true：设置false可以在302对URL跳转时取消显示;jsessionid=xxx的字符串
        // 观察CommonUtils.constructServiceUrl方法可以看到
        initParameters.put("encodeServiceUrl", "false");

        registration.setInitParameters(initParameters);
        // 设定加载的顺序
        registration.setOrder(1);
        return registration;
    }

    /**
     * AuthenticationFilter 认证过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterAuthenticationRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        Map<String, String> initParameters = new HashMap();

        registration.setFilter(new AuthenticationFilter());
        registration.addUrlPatterns("/*");
        initParameters.put("casServerLoginUrl", URLConfig.CAS_SERVER_LOGIN_PATH);
        initParameters.put("serverName", URLConfig.SERVER_NAME);

        // 不拦截的请求 .* 有后缀的文件
        initParameters.put("ignorePattern", ".*");

        // 表示过滤所有
        initParameters.put("ignoreUrlPatternType", "com.liu.SimpleUrlPatternMatcherStrategy");

        registration.setInitParameters(initParameters);
        // 设定加载的顺序
        registration.setOrder(2);
        return registration;
    }

    /**
     * AssertionThreadLocalFilter
     *
     * 该过滤器使得开发者可以通过org.jasig.cas.client.util.AssertionHolder来获取用户的登录名。
     * 比如AssertionHolder.getAssertion().getPrincipal().getName()。
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterAssertionThreadLocalRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AssertionThreadLocalFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        // 设定加载的顺序
        registration.setOrder(1);
        return registration;
    }

    /*
     * HttpServletRequestWrapperFilter wraper过滤器
     * 该过滤器负责实现HttpServletRequest请求的包裹，
     * 比如允许开发者通过HttpServletRequest的getRemoteUser()方法获得SSO登录用户的登录名，可选配置。
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterWrapperRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpServletRequestWrapperFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        // 设定加载的顺序
        registration.setOrder(1);
        return registration;
    }
}
