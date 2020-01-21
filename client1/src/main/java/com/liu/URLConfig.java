package com.liu;

/**
 * Cas 的一些配置项
 */
public class URLConfig {

    /**
     * 当前应用程序的baseUrl（注意最后面的斜线）
     */
    public static String SERVER_NAME = "http://192.168.171.45:8181/";


    /**
     * App1 登出成功url
     */
    public static String APP_LOGOUT_PATH = SERVER_NAME + "logoutSuccess";


    /**
     * CAS服务器地址
     */
    public static String CAS_SERVER_PATH = "http://192.168.171.44:8089/cas";


    /**
     * CAS登陆服务器地址
     */
    public static String CAS_SERVER_LOGIN_PATH = "http://192.168.171.44:8089/cas/login";

    /**
     * CAS登出服务器地址
     */
    public static String CAS_SERVER_LOGOUT_PATH = "http://192.168.171.44:8089/cas/logout";


}
