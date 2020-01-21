
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

    @RequestMapping("test")
    @ResponseBody
    public String test(HttpServletRequest request){
        //可以在request中获取登录账号信息
        String id=request.getRemoteUser();
        System.out.println(id);
        return "test";
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
