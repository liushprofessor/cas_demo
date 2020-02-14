/**
 * Created on [2020/2/14 10:14] by Administrator
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能： TODO(用一句话描述类的功能)
 *
 * ──────────────────────────────────────────
 *   version  变更日期    修改人    修改说明
 * ------------------------------------------
 *   V1.0.0   2020/2/14    Liush     初版
 * ──────────────────────────────────────────
 */
@Controller
@AutoConfigureAfter(ClientProperties.class )
public class LogoutController {

    @Autowired
    private ClientProperties clientProperties;

    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView(clientProperties.casServiceUrl+clientProperties.casContextPath+clientProperties.logoutUrl+"?service="+clientProperties.serviceUrl+"/logout.html");
        request.getSession().invalidate();
       return "redirect:"+clientProperties.casServiceUrl+clientProperties.casContextPath+clientProperties.logoutUrl+"?service="+clientProperties.serviceUrl+clientProperties.clientContextPath+"/logout.html";
    }




}
