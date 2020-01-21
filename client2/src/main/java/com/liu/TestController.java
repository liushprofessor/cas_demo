/**
 * Created on [2020/1/21 13:42] by Administrator
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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能： TODO(用一句话描述类的功能)
 *
 * ──────────────────────────────────────────
 *   version  变更日期    修改人    修改说明
 * ------------------------------------------
 *   V1.0.0   2020/1/21    Liush     初版
 * ──────────────────────────────────────────
 */
@Controller
public class TestController {

    @RequestMapping("test2")
    @ResponseBody
    public String test(HttpServletRequest request){
        //可以在request中获取登录账号信息
        String id=request.getRemoteUser();
        System.out.println(id);
        return "test2";
    }

    //退出登录，先失效session再调用CAS Service
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {

        // session失效
        request.getSession().invalidate();

        return "redirect:" + URLConfig.CAS_SERVER_LOGOUT_PATH + "?service="+URLConfig.APP_LOGOUT_PATH;
    }

    //重定向到退出登录接口，给成功提示语
    @RequestMapping("/logoutSuccess")
    @ResponseBody
    public String logoutSuccess(){


        return "loginSuccess";
    }


}
