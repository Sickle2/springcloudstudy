package com.sickle.servicesecurityoauth2.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: springcloudDemo
 * @description: 想要往token中加入自定义字段需要实现TokenEnhancer
 * @author: sickle
 * @create: 2019-11-21 12:53
 **/
@Component
public class MyTokenEnhancer implements TokenEnhancer {

//    @Autowired
//    private UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//        UserDo user = userService.getTenantUser(authentication.getName());
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("testToken", authentication.getName());
//        additionalInfo.put("tenantId", user.getTenant().getId());
//        additionalInfo.put("userId", user.getUser().getId());
//        additionalInfo.put("userType", user.getUser().getUserType());
//        additionalInfo.put("tenantName", user.getTenant().getTenantName());
//        additionalInfo.put("userName", user.getUser().getUsername());
//        additionalInfo.put("email", user.getUser().getEmail());
//        additionalInfo.put("phone", user.getUser().getPhone());
//        additionalInfo.put("level", user.getTenant().getLevel());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

}
