/**
 * Created on [2020/2/26 9:44] by Administrator
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
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.*;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * 功能： TODO(用一句话描述类的功能)
 *
 * ──────────────────────────────────────────
 *   version  变更日期    修改人    修改说明
 * ------------------------------------------
 *   V1.0.0   2020/2/26    Liush     初版
 * ──────────────────────────────────────────
 */
public class MyCas30ProxyTicketValidationFilter implements GlobalFilter, Ordered {

    protected  Cas20ServiceTicketValidator defaultServiceTicketValidator;
    protected Cas20ProxyTicketValidator defaultProxyTicketValidator;

    protected TicketValidator ticketValidator;

    protected Protocol protocol;

    protected CasClientConfig casClientConfig;

    //cookie存储器
    private CookieHolder cookieHolder;



    public MyCas30ProxyTicketValidationFilter(CasClientConfig casClientConfig,Class<? extends Cas20ServiceTicketValidator> defaultServiceTicketValidatorClass, Class<? extends Cas20ProxyTicketValidator> defaultProxyTicketValidatorClass) {
        this.protocol=Protocol.CAS3;
        this.defaultServiceTicketValidator = new Cas30ServiceTicketValidator(casClientConfig.casServiceUrl);
        this.defaultProxyTicketValidator = new Cas30ProxyTicketValidator(casClientConfig.casServiceUrl);
        this.casClientConfig=casClientConfig;
        ticketValidatorInit();
    }





    //初始化ticket验证器
    protected  TicketValidator ticketValidatorInit(){
        if(!casClientConfig.getAcceptAnyProxy()){
            this.ticketValidator=new Cas30ServiceTicketValidator(casClientConfig.casServiceUrl);
        }else {
            this.ticketValidator=new Cas30ProxyTicketValidator(casClientConfig.casServiceUrl);

        }

        return ticketValidator;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request=exchange.getRequest();
        ServerHttpResponse response=exchange.getResponse();
        if(preFilter(request,response)){
            //从参数中获取ticket参数
            String ticket=GatewayCommonUtils.safeGetParameter(request, this.protocol.getArtifactParameterName());
            //如果ticket参数为空则跳过ticket验证器，进入到认证拦截器，由认证拦截器去跳转到登录页面进行登录
            if(StringUtils.isEmpty(ticket)){
                return chain.filter(exchange);
            }else {
                try {
                    Assertion assertion=ticketValidator.validate(ticket, constructServiceUrl(request));
                    Object authId = request.getCookies().get(casClientConfig.authKey);
                    if(authId==null){
                        //cookie为空跳转到认证服务器去认证
                        return chain.filter(exchange);
                    }
                    cookieHolder.setAttr(authId.toString(),"_const_cas_assertion_",assertion);
                    //跳转回原来访问的地址（去掉url中的ticket参数,使浏览器访问的地址和原来的一样）
                    return GatewayCommonUtils.redirect(exchange, constructServiceUrl(request));

                } catch (TicketValidationException e) {
                    e.printStackTrace();
                }

            }


        }else {
            return chain.filter(exchange);
        }
        return null;
    }



    //将访问的地址编码进行URLEncode后返回
    protected final String constructServiceUrl(ServerHttpRequest request) {
        return GatewayCommonUtils.constructServiceUrl(request,casClientConfig.serviceUrl, this.protocol.getServiceParameterName(), this.protocol.getArtifactParameterName(), true);
    }


    public boolean preFilter(ServerHttpRequest request,ServerHttpResponse response){
        String requestUri=request.getURI().toString();
        if(!StringUtils.isEmpty(casClientConfig.proxyReceptorUrl) && requestUri.endsWith(casClientConfig.proxyReceptorUrl)){
            return false;
        }
        return true;
    }



    @Override
    public int getOrder() {
        return 0;
    }
}
