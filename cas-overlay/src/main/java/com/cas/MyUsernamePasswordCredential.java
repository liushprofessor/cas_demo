
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


    public MyUsernamePasswordCredential(String username, String password, String code,int type) {
        super(username, password);
        this.code = code;
        this.type=type;
    }

    //必须添加空参构造，不然Web Flow无法注入
    public MyUsernamePasswordCredential() {

    }

    private String code;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
