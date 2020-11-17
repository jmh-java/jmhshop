package com.common.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWTExpiredException;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import com.common.entity.JWT.Clams;
import com.common.entity.JWT.StaffDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtils {
    private static final String SECRET = "cf9e4bff-3d4c-4600-be94-9121363c39ar";

    //private static final String SECRETJJC = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW";

    private static final String EXP = "exp";//过期时间

    private static final String PAYLOAD = "userInfo";//主要内容

    private static final String PAYLOADJJC = "payload";//主要内容

    private static final String IAT = "iat";//登录时间

    private static final String NBF = "nbf";//声明标识JWT之前的时间

    private static final String ISS = "iss";//发行人

    private static final String JTI = "jti";//token唯一标识符

    private static final String AUD = "aud";//受众

    private static final long maxAge  = 30L*24L*60L*60L*1000L;//有效期30天


    //加密，传入一个对象和有效期
    public static <T> String sign(T object) {
        try {
            final JWTSigner signer = new JWTSigner(SECRET);
            final Map<String, Object> claims = new HashMap<String, Object>();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(object);
            claims.put(PAYLOADJJC, jsonString);
            claims.put(EXP, System.currentTimeMillis() + maxAge);
            return signer.sign(claims);
        } catch(Exception e) {
            return null;
        }
    }

    //解密，传入一个加密后的token字符串和解密后的类型
    public static<T> T unsign(String jwt, Class<T> classT) {
        final JWTVerifier verifier = new JWTVerifier(SECRET);
        try {
            final Map<String,Object> claims= verifier.verify(jwt);
            if (claims.containsKey(EXP) && claims.containsKey(PAYLOADJJC)) {
                long exp = (Long)claims.get(EXP);
                long currentTimeMillis = System.currentTimeMillis();
                if (exp > currentTimeMillis) {
                    String json = (String)claims.get(PAYLOADJJC);
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(json, classT);
                }else {
                    log.info(ExceptionEnum.TOKEN_ERR.getErrmsg());
                    throw new ApiException(ExceptionEnum.TOKEN_ERR);
                }
            }else {
            log.info(ExceptionEnum.TOKEN_DEFAULT.getErrmsg());
            throw new ApiException(ExceptionEnum.TOKEN_DEFAULT);}
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ApiException(400,e.getMessage());
        }
    }

    //json解密
    public static<T> T unsign1(String json, Class<T> classT) {
        try {
                ObjectMapper objectMapper = new ObjectMapper();
                return  objectMapper.readValue(json, classT);
        } catch (Exception e) {
            return null;
        }
    }

    public static Clams unsignGetClams(String jwt, int type){
        final JWTVerifier verifier = new JWTVerifier(SECRET);
        try {
            final Map<String,Object> claims= verifier.verify(jwt);
//            System.out.println(JSON.toJSONString(claims));
            if (claims.containsKey(PAYLOAD)) {
//                long time = new Date().getTime();
//                if(time> Long.parseLong((String) claims.get(EXP)+"000")){
//                    log.info(ExceptionEnum.TOKEN_ERR.getErrmsg());
//                    throw new ApiException(ExceptionEnum.TOKEN_ERR);
//                }
                Clams clams=new Clams();
                clams.setAud(claims.get(AUD).toString());
                clams.setIat(new Date(Long.parseLong(claims.get(IAT)+"000")));
                clams.setExp(new Date(Long.parseLong(claims.get(EXP)+"000")));
                clams.setNbf(new Date(Long.parseLong(claims.get(NBF)+"000")));
                clams.setIss(claims.get(ISS).toString());
                if(claims.get(JTI) != null){
                    clams.setJti(claims.get(JTI).toString());
                }
                if(type == 1){
                   // UserDTO t = unsign1(claims.get(PAYLOAD).toString(), UserDTO.class);
                    StaffDTO t=JSON.parseObject(claims.get(PAYLOAD).toString(), StaffDTO.class);
                    clams.setSub(t);
                } else {
                	return null;
                }
                return clams;
            }else{
                log.info(ExceptionEnum.TOKEN_DEFAULT.getErrmsg());
                throw new ApiException(ExceptionEnum.TOKEN_DEFAULT);
            }
        } catch (JWTExpiredException e){
        	log.info(e.getMessage());
        	throw new ApiException(410, e.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ApiException(400,e.getMessage());
        }

    }

    public static <T> String signPc(Map claims) {
        try {
            final JWTSigner signer = new JWTSigner(SECRET);
            return signer.sign(claims);
        } catch(Exception e) {
            return null;
        }
    }

    public static StaffDTO getStaff() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Clams clams = (Clams)attributes.getAttribute("clams", 0);
        return clams != null ? clams.getSub() : new StaffDTO();
    }

}
