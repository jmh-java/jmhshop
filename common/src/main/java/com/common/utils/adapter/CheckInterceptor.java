package com.common.utils.adapter;


import com.common.entity.JWT.Clams;
import com.common.utils.ApiException;
import com.common.utils.ExceptionEnum;
import com.common.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class CheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        //        HttpSession session = httpServletRequest.getSession();
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //拦截类上的自定义注解
        UnAuthNoCheck annotation1 = handlerMethod.getMethod().getDeclaringClass().getAnnotation(UnAuthNoCheck.class);
        if (Objects.nonNull(annotation1)) {
            log.info("has UnAuthNoCheck annotation, skip interceptor");
            return true;
        }
        Method method = handlerMethod.getMethod();
        UnAuthNoCheck annotation = method.getAnnotation(UnAuthNoCheck.class);
        if (Objects.nonNull(annotation)) {
            log.info("has UnAuthNoCheck annotation, skip interceptor");
            return true;
        }
        log.info("enter interceptor......");
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = httpServletRequest.getHeaderNames();
        String authorization = null;
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if("authorization".equals(key)) {
                authorization = httpServletRequest.getHeader(key);
                break;
            }
        }
        if(StringUtils.isBlank(authorization)) {
            log.info(ExceptionEnum.TOKEN_MISS.getErrmsg());
            throw new ApiException(ExceptionEnum.TOKEN_MISS);
        }
        String token;
        if(authorization.indexOf("Bearer ") != -1) {
            token = authorization.substring(7);
            if(StringUtils.isBlank(token)) {
                log.info(ExceptionEnum.TOKEN_MISS.getErrmsg() + ",authorization=" + authorization);
                throw new ApiException(ExceptionEnum.TOKEN_MISS);
            }
            Clams clams = JWTUtils.unsignGetClams(token, 1);
            httpServletRequest.setAttribute("clams", clams);
        } else {
        	throw new ApiException(ExceptionEnum.TOKEN_MISS);
        }

//        Cookie[] cookies = httpServletRequest.getCookies();
//        String token=null;
//        for(Cookie cookie : cookies){
//            if(cookie.getName().equals("token")){
//                token=cookie.getValue();
//            }
//        }
//        if(StringUtils.isBlank(token)&&StringUtils.isBlank(map.get("accesstoken"))){
//            log.info(ExceptionEnum.TOKEN_MISS.getErrmsg());
//            throw new ApiException(ExceptionEnum.TOKEN_MISS);
//        }else {
//            //token解密
//            if(StringUtils.isNotBlank(token)){
//                Clams clams = JWTUtils.unsignGetClams(token);
//            }else if(StringUtils.isNotBlank(map.get("accesstoken"))){
//                OperationUser accessToken = JWTUtils.unsign(map.get("accesstoken"), OperationUser.class);
//                log.info(Json.encode(accessToken));
//            }
//
//        }
        log.info("pass interceptor......");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        log.info("URI: {}, method: {}, params: {}",
//                httpServletRequest.getRequestURI(),
//                httpServletRequest.getMethod(),
//                JSON.toJSONString(httpServletRequest.getParameterMap()));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
