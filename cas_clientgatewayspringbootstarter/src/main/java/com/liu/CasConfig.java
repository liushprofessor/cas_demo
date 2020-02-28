/**
 * Created on [2020/2/28 8:47] by Administrator
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

import org.jasig.cas.client.util.ReflectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能： TODO(用一句话描述类的功能)
 *
 * ──────────────────────────────────────────
 *   version  变更日期    修改人    修改说明
 * ------------------------------------------
 *   V1.0.0   2020/2/28    Liush     初版
 * ──────────────────────────────────────────
 */
@Configuration
@EnableConfigurationProperties(CasClientConfig.class)
@AutoConfigureAfter(CasClientConfig.class )
public class CasConfig {

    @Autowired
    private CasClientConfig casClientConfig;

    @Bean
    public CookieHolder cookieHolder(){

        Long[] args = new Long[1];
        args[0] = Long.valueOf(casClientConfig.millisBetweenCleanUps);
        return  ReflectUtils.newInstance(casClientConfig.getCookieHolderPattern(), args);
    }

    @Bean
    public AuthenticationGatewayFilter authenticationGatewayFilter(){


        return new AuthenticationGatewayFilter(casClientConfig,cookieHolder());
    }

    @Bean
    public MyCas30ProxyTicketValidationFilter myCas30ProxyTicketValidationFilter(){

        return new MyCas30ProxyTicketValidationFilter(casClientConfig,cookieHolder());
    }






}
