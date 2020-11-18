package com.common.utils;


import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.DigestUtils;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Utils {


	private static SnowFlake snowFlake = new SnowFlake(1, 1);

    public static List<String> getDates(Date countday, String endTime, String startTime) {
        List<Date>  dates=new ArrayList<>();
        if(endTime.equals(startTime)){
            dates.add(countday);
        }else{
            try {
                // date = format1.parse(parms.getTime1());
                dates=Utils.getDatesBetweenTwoDate(startTime,endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<>();
        for (Date date:
        dates) {
            String format = format1.format(date);
            list.add(format);
        }
        return list;
    }
	
	/**
	 * 雪花唯一id
	 * @return
	 */
	public static String getSnowId(){
		long snowId = snowFlake.nextId();
		return String.valueOf(snowId);
	}

	public static Integer shuffle(Integer num){
        Integer[] randomNum={1,2,3,4,5,6,7,8,9,10};
        List<Integer> list = new ArrayList();
        for(int i = 0;i < randomNum.length;i++){
            list.add(randomNum[i]);
        }
        Collections.shuffle(list);
        return list.get(0)+num;
    }
	
    /**
     * 发邮件
     */
    public static void sendEmail(JavaMailSender mailSender, String[] emailTo, String title, String context, boolean isHtml) {
        String emailForm = "13306149026@163.com";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailForm);
            helper.setTo(emailTo);
            helper.setSubject(title);
            helper.setText(context, isHtml);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实体类转Map
     * @param object
     * @return
     */
    public static Map<String, Object> entityToMap(Object object) {
        Map<String, Object> map = new HashMap();
        for (Field field : object.getClass().getDeclaredFields()){
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
    public static Map<String, String> entityToMapString(Object object) {
        Map<String, String> map = new HashMap();
        for (Field field : object.getClass().getDeclaredFields()){
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                String o = field.get(object).toString();
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 删除list中所有为空的元素
     * 删除为空的操作方式非常复杂，因为你每一次删除一个元素之后，
     * 当前的list的大小就会变化，但是反过来想，如果只是获取它不为
     * 空的元素则不需要考虑它的size大小的变化，而且比较简单。
     * @param list
     * @return
     */
    public static List removeEmptyList(List list) {
        List list1 = new ArrayList();

        if(list==null||list.size()<=0)
            return null;
        //循环第一层
        for(int i=0;i<list.size();i++) {
            //进入每一个list
            List listi = (List) list.get(i);
            if(listi!=null&&listi.size()>0)
                list1.add(listi);
            System.out.println(list1.size());
        }

        return list1;
    }

    /**
     * Map转实体类
     * @param map 需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
     * @param entity  需要转化成的实体类
     * @return
     */
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
        T t = null;
        try {
            t = entity.newInstance();
            for(Field field : entity.getDeclaredFields()) {
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object!= null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }
                    field.setAccessible(flag);
                }
            }
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    /**
     * 根据开始时间和结束时间计算天数
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public static int caculateTotalTime(String startTime,String endTime) throws ParseException{
        SimpleDateFormat formatter =   new SimpleDateFormat( "yyyy-MM-dd" );
        Date date1=null;
        Date date = formatter.parse(startTime);
        long ts = date.getTime();
        date1 =  formatter.parse(endTime);
        long ts1 = date1.getTime();
        long ts2=ts1-ts;
        int totalTime = 0;
        totalTime=(int) (ts2/(24*3600*1000));
        return totalTime;
    }
    /**
     * 判断是否是今日 2019-03-15 并返回昨日
     * @param dateStr
     * @return
     */
    public static String isToday(String dateStr) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date());
        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new ApiException(ExceptionEnum.PARAM_LESS);
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        if(d1 == d2) {
            calendar2.add(Calendar.DATE, -1);
            return sdf.format(calendar2.getTime());
        } else {
            return null;
        }
    }

    /**
     * 在给定的日期加上或减去指定月份后的日期
     *
     * @param sourceDate 原始时间
     * @param month      要调整的月份，向前为负数，向后为正数
     * @return
     */
    public static Date stepMonth(Date sourceDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, month);

        return c.getTime();
    }

    /**
     * list去重
     * @param list
     * @return
     */
    public static void removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
    }

    /**
     * MD5校验
     * @return
     * */
    public static boolean md5Check(String text, String key, String md5){
        text = StringUtils.isBlank(key) ? text : text+key;
        return DigestUtils.md5DigestAsHex(text.getBytes()).equals(md5);
    }

    /**
     * 占比计算保留小数的位数方法
     * 转成百分数
     * 当前数除以总数
     * @param  num1 ,num2  num1/num2
     * @return  rate  保留2位小数的
     */
    public static String  division(int num1,int num2){
        String rate="0.00%";
        //定义格式化起始位数
        String format="0.00";
        if(num2 != 0 && num1 != 0){
            DecimalFormat dec = new DecimalFormat(format);
            rate =  dec.format((double) num1 / num2*100)+"%";
            while(true){
                if(rate.equals(format+"%")){
                    format=format+"0";
                    DecimalFormat dec1 = new DecimalFormat(format);
                    rate =  dec1.format((double) num1 / num2*100)+"%";
                }else {
                    break;
                }
            }
        }else if(num1 != 0 && num2 == 0){
            rate = "100%";
        }
        return rate;
    }

    public static Date getStartTime(Date date) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    public static Date getEndTime(Date date) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取昨日
     * @param today
     * @return
     */
    public static Date getYesterday(Date today) {
        java.sql.Date date = new java.sql.Date(today.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);         //昨天
        return calendar.getTime();        //获取昨天日期
    }

    /**
     * 获取几日前
     * @param today
     * @return
     */
    public static Date getDay(Date today,int i) {
        java.sql.Date date = new java.sql.Date(today.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -i);         //昨天
        return calendar.getTime();        //获取昨天日期
    }

    public static Date getAfterDay(Date today,int i) {
        java.sql.Date date = new java.sql.Date(today.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, i);         //几天后
        return calendar.getTime();        //获取昨天日期
    }
    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     *
     * @param
     * @param
     * @return List
     */
    public static List<Date> getDatesBetweenTwoDate(String start, String end) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = sdf.parse(start);
        Date endDate = sdf.parse(end);
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(beginDate);// 把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);// 把结束时间加入集合
        return lDate;
    }

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     *
     * @param
     * @param
     * @return List
     */
    public static List<Date> getDatesBetweenTwoDateByDate(Date start, Date end) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = sdf.parse(sdf.format(start));
        Date endDate = sdf.parse(sdf.format(end));
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(beginDate);// 把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);// 把结束时间加入集合
        return lDate;
    }

    public static HashMap<String,String> getMd5Sign(Map<String, String[]> map){
        Map<String,String> parmMap=new HashMap<String,String>();
        HashMap<String,String> result=new HashMap<>();
        //参数名称
        Set<String> name=map.keySet();
        //参数迭代器
        Iterator<String> iterator = name.iterator();
        while(iterator.hasNext()){
            String k=iterator.next();
            parmMap.put(k, map.get(k)[0]);
        }
        //判断过来的时间是否在当前时间的前后10分钟内
        Date timeStamp=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long beforeDate = timeStamp.getTime() - 600000;
        long afterDate = timeStamp.getTime() + 600000;
        String timeStamp1 = parmMap.get("timeStamp");
        if(StringUtils.isBlank(timeStamp1)){
            log.info("密钥验证失败，缺失timeStamp参数");
            throw new ApiException(ExceptionEnum.SIGN_ERROR);
        }
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(timeStamp1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = parse.getTime();
        if(time>afterDate||time<beforeDate){
            log.info("时间误差超过10分钟");
            result.put("success","false");
            result.put("msg","时间戳校验未通过");
            result.put("code","401");
            return result;
        }
        String sign=parmMap.get("sign");
        //将parmMap里的sign和appsecert去除
        //log.info("传过来的sign",parmMap.get("sign"));
        parmMap.remove("sign");
        parmMap.remove("appSecret");
        String md5String = null;
        if(parmMap.containsKey("sessionKey")){
        	md5String = getSignRds(parmMap);
        } else {
        	md5String = getSign(parmMap);
        }
        //log.info("生成的sign",md5String);
        if(sign!=null&&md5String!=null&&sign.equals(md5String)){
            //log.info("sign校验通过");
            result.put("success","true");
            result.put("msg","sign校验通过");
            result.put("code","200");
            return result;
        }
        //log.info("sign校验未通过");
        result.put("success","false");
        result.put("msg","sign校验未通过");
        result.put("code","402");
        return result;
    }

    /**
     * 发送信息返回秘钥工具
     * @param parmMap
     * @return
     */
    public static Map<String,String> getSignAddKeyAndTime(Map<String,String> parmMap){
        String appKey=Utils.getProperties("config.properties", "SIGN_APP_KEY");
        if(StringUtils.isBlank(parmMap.get("appKey"))){
            parmMap.put("appKey",appKey);
        }
        if(StringUtils.isBlank(parmMap.get("timeStamp"))){
            Date date=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStamp = simpleDateFormat.format(date);
            parmMap.put("timeStamp",timeStamp);
        }
        String sign = getSign(parmMap);
        parmMap.put("sign",sign);
        return parmMap;
    }

    /**
     * 根据参数生成秘钥  不要将appsecret和sign放在map中
     * @param parmMap
     * @return
     */
    public static String getSign(Map<String,String> parmMap){
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(parmMap);
        StringBuilder sb = new StringBuilder();
        if(sortParams!=null){
            for (Map.Entry<String, String> entry : sortParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!StringUtils.isBlank(value)){
                    value=value.trim();
                    sb.append(key).append(value);
                }
            }
        }
        String appSecret=Utils.getProperties("config.properties", "SIGN_APP_SECRET");
        StringBuffer su=new StringBuffer();
        su.append(appSecret).append(sb).append(appSecret);
        String md5String = null;
        try {
            md5String = DigestUtils.md5DigestAsHex(su.toString().getBytes("UTF-8")).toUpperCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  md5String;
    }
    
    /**
     * 根据redis值验证签名
     * @param parmMap
     * @return
     */
    public static String getSignRds(Map<String,String> parmMap){
        // 将参数以参数名的字典升序排序
    	String sessionKey = "";
    	if(parmMap.containsKey("sessionKey")){
    		sessionKey = parmMap.get("sessionKey");
    		parmMap.remove("sessionKey");
    	}
        Map<String, String> sortParams = new TreeMap<String, String>(parmMap);
        StringBuilder sb = new StringBuilder();
        if(sortParams!=null){
            for (Map.Entry<String, String> entry : sortParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!StringUtils.isBlank(value)){
                    value=value.trim();
                    sb.append(key).append(value);
                }
            }
        }
        String appSecret= "";
        if(StringUtils.isBlank(sessionKey)){
        	appSecret = Utils.getProperties("config.properties", "SIGN_APP_SECRET");
        } else {
        	//appSecret = NoceUtil.secret(sessionKey);
        }
        if(StringUtils.isBlank(appSecret)){
        	return null;
        }
        StringBuffer su=new StringBuffer();
        su.append(appSecret).append(sb).append(appSecret);
        String md5String = null;
        try {
            md5String = DigestUtils.md5DigestAsHex(su.toString().getBytes("UTF-8")).toUpperCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  md5String;
    }

    /**
     * 金额分转元
     * @param fen
     * @return
     */
    public static String fen2yuan(Long fen) {
        if(fen == null) return "";
        BigDecimal bd = new BigDecimal(fen);
        bd = bd.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }

    /**
     * 生成token令牌
     * @param userId
     * @return
     */
    public static String generateToken(String userId) {
        String base64 = Base64.getEncoder().encodeToString(userId.getBytes());
        return base64.substring(1, base64.length()-2);
    }

    /**
     * 字符串转时间
     * @param str
     * @return
     */
    public static Date str2date(String str) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sDateFormat.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     * @param inputString
     * @return
     */
    public static String getPingYin(String inputString) {
        inputString = inputString.replaceAll("[^\u4E00-\u9FA5]", "");
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] input = inputString.trim().toCharArray();
        String output = "";
        try {
            for (int i = 0; i < input.length; i++) {
                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    output += temp[0];
                } else {
                    output += Character.toString(input[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * 获取汉字串拼音首字母，英文字符不变
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
     public static String getFirstSpell(String chinese) {
         chinese = chinese.replaceAll("[^\u4E00-\u9FA5]", "");
         StringBuffer pybf = new StringBuffer();
         char[] arr = chinese.toCharArray();
         HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
         defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
         defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
         for (int i = 0; i < arr.length; i++) {
             if (arr[i] > 128) {
                 try {
                     String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                     if (temp != null) {
                         pybf.append(temp[0].charAt(0));
                     }
                 } catch (BadHanyuPinyinOutputFormatCombination e) {
                     e.printStackTrace();
                 }
             } else {
                 pybf.append(arr[i]);
             }
         }
         return pybf.toString().replaceAll("\\W", "").trim();
     }

     /**
      *  获取汉字串拼音，英文字符不变
      *  @param chinese 汉字串
      *  @return 汉语拼音
      */
     public static String getFullSpell(String chinese) {
         chinese = chinese.replaceAll("[^\u4E00-\u9FA5]", "");
         StringBuffer pybf = new StringBuffer();
         char[] arr = chinese.toCharArray();
         HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
         defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
         defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
         for (int i = 0; i < arr.length; i++) {
             if (arr[i] > 128) {
                 try {
                     pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                 } catch (BadHanyuPinyinOutputFormatCombination e) {
                     e.printStackTrace();
                 }
             } else {
                 pybf.append(arr[i]);
             }
         }
         return pybf.toString();
     }

     /**
 	 * 中文全角符号转半角符号
 	 */
 	public static String full2Half(String string) {
 		if (StringUtils.isBlank(string)) {
 			return string;
 		}
 		char[] charArray = string.toCharArray();
 		for (int i = 0; i < charArray.length; i++) {
 			if (charArray[i] == 12288) {
 				charArray[i] = (char) 32;
 			} else if (charArray[i] >= 65281 && charArray[i] <= 65374) {
 				charArray[i] = (char) (charArray[i] - 65248);
 			}

 		}

 		return new String(charArray);
 	}

 	/**
 	 * 字符串前后补0
 	 * @param sourStr
 	 * @param ch
 	 * @param maxLength
 	 * @param isPrefix
 	 * @return
 	 */
 	public static String supplyCharForString(String sourStr, String ch,
			int maxLength, boolean isPrefix) {
		if (StringUtils.isBlank(ch)) {
			return null;
		} else {
			String tmp = "";
			if (sourStr != null) {
				for (int i = 0; i < maxLength - sourStr.length(); i++) {
					tmp = tmp + ch;
				}
				if (isPrefix) {
					return tmp + sourStr;
				} else {
					return sourStr + tmp;
				}
			} else {
				for (int i = 0; i < maxLength; i++) {
					tmp = tmp + ch;
				}
				return tmp;
			}
		}
	}

    public static void checkClientAndStore(String clientId, String storeId) {
 	    if(StringUtils.isBlank(clientId))
 	        throw new ApiException(ExceptionEnum.CLIENT_NOT_EMPTY);
        if(StringUtils.isBlank(storeId))
            throw new ApiException(ExceptionEnum.STORE_NOT_EMPTY);
    }

    //生成uuid工具类
    public static String createUUID(){
 	    return UUID.randomUUID().toString().replaceAll("-", "");
    }

	/**
	 * 获得当天零时零分零秒
	 *
	 * @return
	 */
	public static Date initDateByDay(Date dt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 根据类型加密 
	 * @return
	 * */
	public static String JMType(String password,String type){
    	//判断加密方式，默认明码
    	if(type!=null){
    		switch(type){
    		case "0": return password;
    		case "1": return DigestUtils.md5DigestAsHex(password.getBytes());
    		}
    	}
    	return password;
    }
	
	/**
	 * 获得当天零时零分零秒
	 * @return
	 */
	public static Date initDateByDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

    /**	 *
     * 正则替换所有特殊字符	 * @param orgStr	 * @return
     * */
    public static String replaceSpecStr(String orgStr){
        if (null!=orgStr&&!"".equals(orgStr.trim())) {
            String regEx="[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(orgStr);
            return m.replaceAll("");
        }
        return null;
    }

    //获取本周的开始时间
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d){
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //日期格式化
    public static String getDateString(Date date){
        SimpleDateFormat sDateFormat=new SimpleDateFormat("MM-dd");
        String dateString=sDateFormat.format(date);
        return dateString;
    }
    //日期格式化
    public static String getDateStringYMD(Date date){
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String dateString=sDateFormat.format(date);
        return dateString;
    }

    //获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    //获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    //获取本周是今年的第几周
    public static Integer getNowWeekOfYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置星期一为一周开始的第一天
        calendar.setTimeInMillis(System.currentTimeMillis());//获得当前的时间戳
        int weekYear = calendar.get(Calendar.YEAR);//获得当前的年
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);//获得当前日期属于今年的第几周
        return weekOfYear;
    }

    //两个日期相减得到的天数
    public static int getDiffDays(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }
        long diff = (endDate.getTime() - beginDate.getTime())/ (1000 * 60 * 60 * 24);
        int days = new Long(diff).intValue();
        return days;
    }

    //今天为最后一天 一周前
    public static String getWeekBefore() {
        String paramStartDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNow = new Date();
        Date dateBefore = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateNow);
        cal.add(Calendar.DAY_OF_MONTH, -6);
        dateBefore = cal.getTime();
        paramStartDate = sdf.format(dateBefore);
        return paramStartDate;
    }

    //今天为最后一天 n月前
    public static String getMonthBefore(int n) {
        String paramStartDate = "";
        String paramEndDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNow = new Date();
        Date dateBefore = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateNow);
        cal.add(Calendar.MONTH, -n);
        dateBefore = cal.getTime();
        paramEndDate = sdf.format(dateNow);
        paramStartDate = sdf.format(dateBefore);
        return paramStartDate;
    }

	public static String getProperties(String filePath, String keyWord) {
		Properties prop = null;
		String value = null;
		try {
			// 通过Spring中的PropertiesLoaderUtils工具类进行获取
			prop = PropertiesLoaderUtils.loadAllProperties(filePath);
			// 根据关键字查询相应的值
			value = prop.getProperty(keyWord);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

    /**
     * 获取门店自增编码
     * @param a  品牌自增码 AutoNumber
     * @param b  门店编号   storeNumber
     * @return
     */
	public static String getShopAutoNumber(int a ,int b){
        String newA="";
        String newB="";
        if(a<10){
            newA="100"+a;
        }else if(a<100){
            newA="10"+a;
        }else if(a<1000){
            newA="1"+a;
        }else {
            newA=a+"";
        }

        if(b<10){
            newB="000"+b;
        }else if(b<100){
            newB="00"+b;
        }else if(b<1000){
            newB="0"+b;
        }else {
            newB=b+"";
        }
        return newA+newB;
    }



    //获取随机工单号id
    public String getAffairId(){
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyyMMdd");
        String dateString=sDateFormat.format(new Date());
        int count=(int)((Math.random()*9+1)*1000);
	    return dateString+count;
    }

    /*public static UserRecord recode(String a,String b){
        UserDTO user = JWTUtils.getUser();
        if(user==null){
            throw new ApiException(ExceptionEnum.TOKEN_DEFAULT);
        }
        UserRecord userRecord=new UserRecord();
        userRecord.setCompanyid(user.getCompanyId());
        userRecord.setStaffid(user.getStaffId());
        userRecord.setStaffname(user.getStaffName());
        userRecord.setCreatetime(new Date());
        userRecord.setCode(a);
        userRecord.setName(b);
        userRecord.setId(Utils.createUUID());
        return userRecord;
    };

    public static UserRecord recode(String a,String b,String content){
        UserDTO user = JWTUtils.getUser();
        if(user==null){
            throw new ApiException(ExceptionEnum.TOKEN_DEFAULT);
        }
        UserRecord userRecord=new UserRecord();
        userRecord.setCompanyid(user.getCompanyId());
        userRecord.setStaffid(user.getStaffId());
        userRecord.setStaffname(user.getStaffName());
        userRecord.setCreatetime(new Date());
        userRecord.setCode(a);
        userRecord.setName(b);
        userRecord.setContent(content);
        userRecord.setId(Utils.createUUID());
        return userRecord;
    };

    public static UserRecord recode(String a,String b,String content,String dealid){
        UserDTO user = JWTUtils.getUser();
        if(user==null){
            throw new ApiException(ExceptionEnum.TOKEN_DEFAULT);
        }
        UserRecord userRecord=new UserRecord();
        userRecord.setCompanyid(user.getCompanyId());
        userRecord.setStaffid(user.getStaffId());
        userRecord.setStaffname(user.getStaffName());
        userRecord.setCreatetime(new Date());
        userRecord.setCode(a);
        userRecord.setName(b);
        userRecord.setContent(content);
        userRecord.setId(Utils.createUUID());
        userRecord.setDealid(dealid);
        return userRecord;
    }*/

    /**
     * 过滤特殊字符
     * @param str
     * @return
     */
    public static String stringFilter (String str){
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（） ——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String res = m.replaceAll("").trim();
        return StringUtils.isNotBlank(res) ? res : Utils.createUUID();
    }

    //查看是否过期
    public static Boolean isOverdue(String endDate){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());

            Date d_now=sdf.parse(date);
            Date d_end=sdf.parse(endDate);
            int flag = d_end.compareTo(d_now);
            if (flag >= 0) {//当天及当天之后，<0 就是在日期之前
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    //查看是否过期
    public static Boolean isOverdue(Date end){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String endDate=sdf.format(end);
            String date = sdf.format(new Date());

            Date d_now=sdf.parse(date);
            Date d_end=sdf.parse(endDate);
            int flag = d_end.compareTo(d_now);
            if (flag >= 0) {//当天及当天之后，<0 就是在日期之前
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 判断网络图片是否存在
     * posturl 图片地址链接
     */
    public static boolean isImagesTrue(String posturl) throws IOException {
//        if(StringUtils.isBlank(posturl)) return false;
        URL url = new URL(posturl);
        HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
        urlcon.setRequestMethod("POST");
        urlcon.setRequestProperty("Content-type",
                "application/x-www-form-urlencoded");
        if (urlcon.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println(HttpURLConnection.HTTP_OK + posturl
                    + ":posted ok!");
            return true;
        } else {
            System.out.println(urlcon.getResponseCode() + posturl
                    + ":Bad post...");
            return false;
        }
    }

    /**
     * 将{@link BufferedImage}生成formatName指定格式的图像数据
     * @param source
     * @param formatName 图像格式名，图像格式名错误则抛出异常
     * @return
     */
    public static byte[] wirteBytes(BufferedImage source,String formatName){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        BufferedImage newBufferedImage = new BufferedImage(source.getWidth(),
                source.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newBufferedImage.createGraphics();
        try {
            g.drawImage(source, 0, 0,null);
            if(!ImageIO.write(newBufferedImage, formatName, output))
                throw new IllegalArgumentException(String.format("not found writer for '%s'",formatName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally{
            g.dispose();
        }
        return output.toByteArray();
    }

    //判断当前时间是否在指定时间半小时前
    public static Boolean beforeSemih(String time){
        try {
            long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=df.parse(time);
            if(currentTime<date.getTime()){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    //判断当前时间是否在指定时间X小时前
    public static Boolean beforeSemih(String time,Integer hour){
        try {
            long currentTime = System.currentTimeMillis() + 60 * 60 * 1000*hour;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=df.parse(time);
            if(currentTime<date.getTime()){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将异常堆栈转换为字符串
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        throwable.printStackTrace(printWriter);
        return result.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(getSnowId());
//    	System.out.println(createUUID());
	}

}
