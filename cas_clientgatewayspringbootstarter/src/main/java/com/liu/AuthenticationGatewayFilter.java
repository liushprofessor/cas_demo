/**
 * Created on [2020/2/20 9:06] by Administrator
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

import org.jasig.cas.client.Protocol;
import org.jasig.cas.client.authentication.ContainsPatternUrlPatternMatcherStrategy;
import org.jasig.cas.client.authentication.ExactUrlPatternMatcherStrategy;
import org.jasig.cas.client.authentication.RegexUrlPatternMatcherStrategy;
import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.ReflectUtils;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.session.WebSessionIdResolver;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 功能： TODO(用一句话描述类的功能)
 * <p>
 * ──────────────────────────────────────────
 * version  变更日期    修改人    修改说明
 * ------------------------------------------
 * V1.0.0   2020/2/20    Liush     初版
 * ──────────────────────────────────────────
 */
public class AuthenticationGatewayFilter implements GlobalFilter, Ordered {

    //带在url后的参数，登录和获取ticket带在url后的参数名设置
    private Protocol protocol;

    //白名单鉴权器
    private UrlPatternMatcherStrategy ignoreUrlPatternMatcherStrategyClass;

    //白名单鉴权容器
    private static final Map<String, Class<? extends UrlPatternMatcherStrategy>> PATTERN_MATCHER_TYPES = new HashMap();

    //配置信息
    private CasClientConfig casClientConfig;

    //cookie存储器
    private CookieHolder cookieHolder;

    //初始化白名单鉴权容器
    static {
        PATTERN_MATCHER_TYPES.put("CONTAINS", ContainsPatternUrlPatternMatcherStrategy.class);
        PATTERN_MATCHER_TYPES.put("REGEX", RegexUrlPatternMatcherStrategy.class);
        PATTERN_MATCHER_TYPES.put("EXACT", ExactUrlPatternMatcherStrategy.class);
    }


    public AuthenticationGatewayFilter(CasClientConfig casClientConfig,CookieHolder cookieHolder) {
        this.casClientConfig = casClientConfig;
        this.protocol = Protocol.CAS2;
        //从容器中获取白名单验证器类型,默认正则方式
        Class ignoreUrlPatternClass = PATTERN_MATCHER_TYPES.get(casClientConfig.getIgnoreUrlPatternType());
        //用反射新建白名单验证器类
        this.ignoreUrlPatternMatcherStrategyClass = (UrlPatternMatcherStrategy) ReflectUtils.newInstance(ignoreUrlPatternClass.getName(), new Object[0]);
        //如果鉴权器不为空
        if (this.ignoreUrlPatternMatcherStrategyClass != null) {
            this.ignoreUrlPatternMatcherStrategyClass.setPattern(casClientConfig.whiteUrl);
        }

        this.cookieHolder=cookieHolder;
    }


    //将访问的地址编码进行URLEncode后返回
    protected final String constructServiceUrl(ServerHttpRequest request) {
        return GatewayCommonUtils.constructServiceUrl(request, this.protocol.getServiceParameterName(), this.protocol.getArtifactParameterName(), true,false);
    }


    private boolean isRequestUrlExcluded(ServerHttpRequest request) {
        if (this.ignoreUrlPatternMatcherStrategyClass == null) {
            return false;
        } else {
            StringBuffer urlBuffer = new StringBuffer(request.getURI().toString());
            if (request.getURI().getQuery() != null) {
                urlBuffer.append("?").append(request.getURI().getQuery());
            }

            String requestUri = urlBuffer.toString();
            return this.ignoreUrlPatternMatcherStrategyClass.matches(requestUri);
        }
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response=exchange.getResponse();
        //如果是白名单跳过拦截器
        if (this.isRequestUrlExcluded(request)) {
            return chain.filter(exchange);
        }
        Object authId = request.getCookies().get(casClientConfig.authKey);
        if (authId == null) {
            //在前一个拦截器中已经验证过cookie
            authId = UUID.randomUUID().toString();
            response.addCookie(ResponseCookie.from(casClientConfig.authKey,authId.toString()).build());
        }
        //从已经登录的容器中获取登录信息
        Assertion assertion = (Assertion) cookieHolder.getAttr(authId.toString(),"_const_cas_assertion_");
        //如果已经存在登录信息应用之前已经登录，直接跳过
        if (assertion != null) {
            return chain.filter(exchange);
        } else {
            //如果没有验证过ticket，说明还未登录过，重定向至cas服务端登录，并且带上登录成功后的回调地址
            String serviceUrl = this.constructServiceUrl(request);
            String urlToRedirectTo = GatewayCommonUtils.constructRedirectUrl(casClientConfig.casServiceUrl + casClientConfig.casContextPath + casClientConfig.loginUrl, this.protocol.getServiceParameterName(), serviceUrl);
            return GatewayCommonUtils.redirect(exchange, urlToRedirectTo);

        }


    }

    protected String retrieveTicketFromRequest(ServerHttpRequest request) {
        return GatewayCommonUtils.safeGetParameter(request, this.protocol.getArtifactParameterName());
    }


    @Override
    public int getOrder() {
        return -99;
    }
}
