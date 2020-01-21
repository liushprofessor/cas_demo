/**
 * Created on [2020/1/10 9:26] by Administrator
 * <p>
 * 项目名称： CAS_SSO_Record-master TODO(项目名称)
 * <p>
 * 本程序版权属于福建慧政通信息科技有限公司所有。
 * 任何组织和个人未经福建慧政通信息科技有限公司许可与授权,不得擅自传播、复制、更改该程序的内容。
 * 本程序受版权法和国际条约的保护。如未经授权而擅自复制或传播本程序(或其中任何部分),
 * 将受到严厉的刑事及民事制裁，并将在法律许可的范围内受到最大可能的起诉!
 * <p>
 * ©2020 福建慧政通信息科技有限公司
 */
package com.cas;

import org.apereo.cas.web.flow.ServiceAuthorizationCheck;
import org.apereo.cas.web.flow.actions.InitialAuthenticationAction;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer;
import org.apereo.cas.web.flow.configurer.DefaultLoginWebflowConfigurer;
import org.apereo.cas.web.flow.configurer.DefaultLogoutWebflowConfigurer;
import org.apereo.cas.web.flow.configurer.GroovyWebflowConfigurer;
import org.apereo.cas.web.flow.login.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能： TODO(用一句话描述类的功能)
 *
 * ──────────────────────────────────────────
 *   version  变更日期    修改人    修改说明
 * ------------------------------------------
 *   V1.0.0   2020/1/10    Liush     初版
 * ──────────────────────────────────────────
 */
@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/test")
    public String test(){

        return "test";
    }


}
