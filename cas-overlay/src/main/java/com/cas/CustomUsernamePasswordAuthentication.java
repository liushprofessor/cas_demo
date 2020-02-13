package com.cas;

import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

/**
 * @author Liush
 * @description
 * @date 2019/12/19 10:41
 **/
public class CustomUsernamePasswordAuthentication extends AbstractPreAndPostProcessingAuthenticationHandler {

    private UserMapper userMapper;


    public CustomUsernamePasswordAuthentication(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order,UserMapper userMapper) {
        super(name, servicesManager, principalFactory, order);
        this.userMapper=userMapper;
    }

    @Override
    public boolean supports(Credential credential){

        return credential instanceof MyUsernamePasswordCredential ;

    }



    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        MyUsernamePasswordCredential myUsernamePasswordCredential=(MyUsernamePasswordCredential)credential;
        //如果是方式1验证
        if(myUsernamePasswordCredential.getType()==1){
            String userId=userMapper.findUserName
                    (myUsernamePasswordCredential.getUsername(),myUsernamePasswordCredential.getPassword());
            if(userId==null){
                throw new FailedLoginException("账号密码错误");
            }
            if(!"code".equals(((MyUsernamePasswordCredential) credential).getCode())){

                throw new CodeAuthenticationException("验证码错误");
            }
            return createHandlerResult(credential,this.principalFactory.createPrincipal(credential.getId()),new ArrayList<>());

        }
        //如果是方式2验证
        if(myUsernamePasswordCredential.getType()==2){

            if("18065050022".equals(myUsernamePasswordCredential.getUsername())){

                return createHandlerResult(credential,this.principalFactory.createPrincipal(credential.getId()),new ArrayList<>());

            }

        }


        throw new FailedLoginException("账号密码错误");

    }
}
