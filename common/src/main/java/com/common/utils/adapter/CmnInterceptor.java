package com.common.utils.adapter;

import com.common.entity.JWT.Clams;
import com.common.utils.ApiException;
import com.common.utils.Constant;
import com.common.utils.JWTUtils;
import com.common.utils.Utils;
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

/**
 * sass拦截器验证
 */
@Slf4j
public class CmnInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //拦截类上的自定义注解
        UnAuthNoCheck annotation1 = handlerMethod.getMethod().getDeclaringClass().getAnnotation(UnAuthNoCheck.class);
        if (Objects.nonNull(annotation1)) {
            log.info("cmnapi, has UnAuthNoCheck annotation, skip interceptor");
            return true;
        }
        
        UnAuthNoCheck annotation = method.getAnnotation(UnAuthNoCheck.class);
        if (Objects.nonNull(annotation)) {
            log.info("cmnapi, has UnAuthNoCheck annotation, skip interceptor");
            return true;
        }
        log.info("enter cmnapi interceptor......");
        
        // 读取头信息
        Map<String, String[]> map = new HashMap<String, String[]>();
        Enumeration headerNames = request.getHeaderNames();
        String token = "";
        String userid = "";
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if("token".equals(key)) {
            	token = request.getHeader(key);
            }
            if("sign".equals(key)) {
            	String sign = request.getHeader(key);
            	if(StringUtils.isBlank(sign)) {
            		throw new ApiException(401,"缺少校验参数");
                } else {
                	map.put("sign", new String[]{sign});
                }
            }
            if("noncestr".equals(key)) {
            	String nonceStr = request.getHeader(key);
            	if(StringUtils.isBlank(nonceStr)) {
            		throw new ApiException(401,"缺少校验参数");
                } else {
                	map.put("nonceStr", new String[]{nonceStr});
                }
            }
            if("appkey".equals(key)) {
            	String appKey = request.getHeader(key);
            	if(StringUtils.isBlank(appKey)) {
            		throw new ApiException(401,"缺少校验参数");
                } else {
                	map.put("appKey", new String[]{appKey});
                }
            }
            if("timestamp".equals(key)) {
            	String timeStamp = request.getHeader(key);
            	if(StringUtils.isBlank(timeStamp)) {
            		throw new ApiException(401,"缺少校验参数");
                } else {
                	map.put("timeStamp", new String[]{timeStamp});
                }
            }
        }
        
        if(map==null || map.isEmpty() || map.get("appKey") == null || map.get("nonceStr") == null || map.get("timeStamp") == null || map.get("sign") == null){
            throw new ApiException(401,"缺少校验参数");
        }
        
        if(StringUtils.isBlank(userid) || !Constant.CMN_SESSION_KEY.contains(userid)){
    		throw new ApiException(401,"sessionKey验证失败");
    	}
        
        if(!StringUtils.isBlank(userid) && !"null".equals(userid)){
    		String nonce = map.get("nonceStr") == null ? "" : map.get("nonceStr")[0];
    		String key =  "cmn:sign_" + userid;
//    		if(!NoceUtil.exsit(key, nonce)){
//    			throw new ApiException(401,"重复请求拦截!");
//    		}
    	}

        HashMap<String, String> md5Sign = Utils.getMd5Sign(map);
        if(md5Sign.get("success").equals("false")){
            if("401".equals(md5Sign.get("code"))){
                throw new ApiException(401,md5Sign.get("msg"));
            }else {
                throw new ApiException(402,md5Sign.get("msg"));
            }
        }
        
        //解析token
        if(!StringUtils.isBlank(token)){
	        Clams clams = JWTUtils.unsignGetClams(token, 2);
	        request.setAttribute("clams", clams);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        log.info("URI: {}, method: {}, params: {}",
//                httpServletRequest.getRequestURI(),
//                httpServletRequest.getMethod(),
//                JSON.toJSONString(httpServletRequest.getParameterMap()));
    }
}
