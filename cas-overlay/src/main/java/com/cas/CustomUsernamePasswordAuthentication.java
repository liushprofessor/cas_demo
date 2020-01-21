package com.cas;

import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

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
        String userId=userMapper.findUserName
                (myUsernamePasswordCredential.getUsername(),myUsernamePasswordCredential.getPassword());
        if( userId!=null
                    && "code".equals(((MyUsernamePasswordCredential) credential).getCode())

                ){
            List<MessageDescriptor> list = new ArrayList<>();
            return createHandlerResult(credential,this.principalFactory.createPrincipal(credential.getId()),list);
        }
        throw new FailedLoginException("登录失败 ");
    }
}
