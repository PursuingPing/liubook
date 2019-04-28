package com.classbooking.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.classbooking.web.domain.User;
import com.classbooking.web.util.Constants;
import com.classbooking.web.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 判断用户权限的Spring MVC的拦截器
 */
public class AuthorizedInterceptor  implements HandlerInterceptor {

    /** 定义不需要拦截的请求 */
//    private static final String[] IGNORE_URI = {"/login.html", "/home.html","/404.html","*.js","*.css"};

    private List<String> excludedUrls;

    @Autowired
    private RedisUtil redisUtil;

    public List<String> getExceptUrls() {
        return excludedUrls;
    }
    public void setExcludedUrls(List<String> excludedUrls){
        this.excludedUrls= excludedUrls;
    }

    /**
     * 该方法需要preHandle方法的返回值为true时才会执行。
     * 该方法将在整个请求完成之后执行，主要作用是用于清理资源。
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception exception)
            throws Exception {

    }

    /**
     * 这个方法在preHandle方法返回值为true的时候才会执行。
     * 执行时间是在处理器进行处理之 后，也就是在Controller的方法调用之后执行。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView mv) throws Exception {

    }

    /**
     * preHandle方法是进行处理器拦截用的，该方法将在Controller处理之前进行调用，
     * 当preHandle的返回值为false的时候整个请求就结束了。
     * 如果preHandle的返回值为true，则会继续执行postHandle和afterCompletion。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        /** 默认用户没有登录 */
        /** 获得请求的ServletPath */
        String servletPath = request.getServletPath();
        /**  判断请求是否需要拦截 */
        String requestUri = request.getRequestURI();
        if(requestUri.startsWith(request.getContextPath())){
            requestUri = requestUri.substring(request.getContextPath().length(), requestUri.length());
        }
        //系统根目录
        if (StringUtils.equals("/",requestUri)) {
            return true;
        }
        //放行exceptUrls中配置的url
        for (String url:excludedUrls) {
            if (url.endsWith("/**")) {
                if (requestUri.startsWith(url.substring(0, url.length() - 3))) {
                    return true;
                }
            } else if (requestUri.startsWith(url)) {
                return true;
            }
        }
        /** 拦截请求 */
            /** 1.获取session中的用户  */
            String tokenFormWeb = request.getHeader("Authorization");
            if( tokenFormWeb==null || tokenFormWeb==""){
                System.out.println("拦截成功"+requestUri);
                return false;
            }
            String user = redisUtil.get(tokenFormWeb);
            //User user = (User) request.getSession().getAttribute("user");
            /** 2.判断用户是否已经登录 */
            if(user==null || user==""){
                System.out.println("拦截成功"+requestUri);
                /** 如果用户没有登录，跳转到登录页面 */
//                request.setAttribute("message", "请先登录再访问网站!");
//                request.getRequestDispatcher(Constants.LOGIN).forward(request, response);
                Map<String ,Object> result = new HashMap<>();
                result.put("sucess",false);
                result.put("code",1000);
                result.put("message","用户未登录");
                response.getWriter().print(new JSONObject(result));
                return false;
            }else{
                 return true;
            }

    }

}
