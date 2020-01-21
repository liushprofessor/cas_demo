
package com.cas;

import org.apereo.cas.authentication.UsernamePasswordCredential;

/**
 * 功能： TODO(用一句话描述类的功能)
 *
 * ──────────────────────────────────────────
 *   version  变更日期    修改人    修改说明
 * ------------------------------------------
 *   V1.0.0   2020/1/19    Liush     初版
 * ──────────────────────────────────────────
 */
public class MyUsernamePasswordCredential extends UsernamePasswordCredential {


    public MyUsernamePasswordCredential(String username, String password, String code) {
        super(username, password);
        this.code = code;
    }

    //必须添加空参构造，不然Web Flow无法注入
    public MyUsernamePasswordCredential() {

    }

    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
