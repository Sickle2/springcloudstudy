//package com.sickle.servicezuul.config;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
///**
// * @program: springcloudDemo
// * @description: filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
// * pre：路由之前
// * routing：路由之时
// * post： 路由之后
// * error：发送错误调用
// * filterOrder：过滤的顺序
// * shouldFilter：这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
// * run：过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
// * @author: sickle
// * @create: 2019-08-15 10:58
// **/
//
//@Component
//public class MyFilter extends ZuulFilter {
//
//    private AntPathMatcher pathMatcher = new AntPathMatcher();
//
//    private static final String[] permitPathPattern = new String[]{"/**/auth/**", "/**/monitor/**", "/**/member/tenant",
//            "/**/actuator/**", "/**/assets/**", "/**/recharge/wxNotify/**", "/**/recharge/aliNotify/**", "/**/swagger-resources/**", "/**/swagger-ui.html/**",
//            "/**/login/**", "/**/customer/**", "/**/wechat/**", "/**/callback/**", "/**/unauth/**", "/**/templates/**", "/**/member/user/phone/**",
//            "/**/member/user/password/**", "/**/member/vcode/**", "/**/convertUrl/**"};
//
//    private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN,token,username,client, Content-Disposition";
//    private static final String ALLOWED_METHODS = "GET,POST,OPTIONS,PUT,DELETE";
//    private static final String ALLOWED_ORIGIN = "*";
//    private static final String ALLOWED_EXPOSE = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN,token,username,client";
//    private static final String MAX_AGE = "18000L";
//
//    @Override
//    public String filterType() {
//        return FilterConstants.PRE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return 0;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    /**
//     * @Description: 过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问
//     * @Author: sickle
//     * @Date: 2019/8/15
//     */
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//
//        HttpServletResponse response = ctx.getResponse();
//        response.addHeader("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
//        response.setHeader("Access-Control-Allow-Methods", ALLOWED_METHODS);
//        response.setHeader("Access-Control-Max-Age", MAX_AGE);
//        response.setHeader("Access-Control-Allow-Headers", ALLOWED_HEADERS);
//        response.setHeader("Access-Control-Expose-Headers", ALLOWED_EXPOSE);
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        String method = request.getMethod();
//
//        //过滤vue的options请求
//        if (RequestMethod.OPTIONS.name().equalsIgnoreCase(method)) {
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(200);
//            return null;
//        }
//
//        for (String pathPattern : permitPathPattern) {
//            if (pathMatcher.match(pathPattern, request.getRequestURI())) {
//                ctx.setSendZuulResponse(true);
//                ctx.setResponseStatusCode(200);
//                return null;
//            }
//        }
//
//        String token = request.getHeader("Authorization");
//        if (token == null || token.isEmpty()) {
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(401);
//            //返回的信息
//            ctx.setResponseBody("水水水水水");
//            return null;
//        } else {
//            ctx.setSendZuulResponse(true);
//            ctx.setResponseStatusCode(200);
//            return null;
//        }
//    }
//}
