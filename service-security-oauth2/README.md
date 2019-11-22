##OAuth 2.0提供程序
OAuth 2.0提供程序机制负责公开受OAuth 2.0保护的资源。
该配置涉及建立OAuth 2.0客户端，这些客户端可以独立或代表用户访问其受保护的资源。
提供者通过管理和验证用于访问受保护资源的OAuth 2.0令牌来做到这一点。
如果适用，提供者还必须为用户提供一个界面，以确认可以授予客户端访问受保护资源的权限（即确认页面）。
##OAuth 2.0提供程序实现
OAuth 2.0中的提供者角色实际上是在授权服务和资源服务之间分配的，
尽管它们有时驻留在同一应用程序中，但使用Spring Security OAuth，
您可以选择将它们拆分到两个应用程序中，并具有多个共享的资源服务授权服务。
对令牌的请求由Spring MVC控制器端点处理，对受保护资源的访问由标准
Spring Security请求过滤器处理。为了实现OAuth 2.0授权服务器，
Spring Security过滤器链中需要以下端点：
* AuthorizationEndpoint用于服务于授权请求。预设网址：/oauth/authorize。
* TokenEndpoint用于服务访问令牌的请求。预设网址：/oauth/token。

要实现OAuth2.0资源服务器，需要以下过滤器：
* 将`OAuth2AuthenticationProcessingFilter` 用于为给定经过身份验证的访问令牌的请求加载身份验证。

##授权服务器配置
在配置授权服务器时，必须考虑客户端用于从最终用户获取访问令牌的授予类型（例如，授权代码，用户凭证，刷新令牌）。服务器的配置用于提供客户端详细信息服务和令牌服务的实现，
并全局启用或禁止用该机制的某些方面。但请注意，可以为每个客户端配置专门的权限，使其能够使用某些授权机制和访问授权。
也就是说，仅因为您的提供者配置为支持“客户端凭据”授予类型，并不意味着授权特定的客户端使用该授予类型。

该`@EnableAuthorizationServer`批注用于配置OAuth 2.0授权服务器机制以及任何`@Beans`实现的机制`AuthorizationServerConfigurer`（有一个便捷的适配器实现，其中包含空方法）。以下功能委托给由Spring创建并传递到的单独的配置器`AuthorizationServerConfigurer`：

- `ClientDetailsServiceConfigurer`：定义客户端详细信息服务的配置程序。可以初始化客户端详细信息，或者您可以仅引用现有商店。
- `AuthorizationServerSecurityConfigurer`：定义令牌端点上的安全约束。
- `AuthorizationServerEndpointsConfigurer`：定义授权和令牌端点以及令牌服务。
提供者配置的一个重要方面是将授权代码提供给OAuth客户端的方式（在授权代码授予中）。OAuth客户端通过将最终用户定向到授权页面（用户可以在其中输入她的凭据）来获得授权码，从而导致从提供者授权服务器重定向到使用授权码的OAuth客户端。OAuth 2规范中对此进行了详细说明。

https://www.oschina.net/translate/oauth-2-developers-guide?lang=chs&p=1
https://projects.spring.io/spring-security-oauth/docs/oauth2.html


#生成秘钥
-   `keytool -genkeypair -alias mytest -keyalg RSA -keypass mypass -keystore mytest.jks -storepass mypass`
-   `keytool -list -rfc --keystore mytest.jks | openssl x509 -inform pem -pubkey`
