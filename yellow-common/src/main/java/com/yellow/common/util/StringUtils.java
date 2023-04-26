package com.yellow.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String valueOf(Object o){
        return Objects.isNull(o) ? null : o.toString();
    }

    public static String other(String value, String other){
        return isEmpty(value) ? other : value;
    }


    /**
     * 对BeanUtils.copyProperties复制数据是非空的处理
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * BeanUtils.copyProperties的非空版本
     * @param src 发送者
     * @param target 接收者
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }


    /**
     * 手机号脱敏
     * @param mobile
     * @return
     */
    public static String phoneEncrypt(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
    }

    /**
     * 身份证号脱敏
     * @param identity
     * @return
     */
    public static String identityEncrypt(String identity) {
        if (StringUtils.isEmpty(identity) || (identity.length() != 18)) {
            return identity;
        }
        return identity.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
    }

    /**
     * 姓名脱敏
     * @param name
     * @return
     */
    public static String nameEncrypt(String name ){
        if(Objects.nonNull(name)){
            if(name.length()==2){
                //截取name 字符串截取第一个字符，
                return name.charAt(0)+"*" ;
            }else if(name.length()==3){
                //截取第一个和第三个字符
                return name.charAt(0)+"*"+name.charAt(2);
            }else if(name.length()==4){
                //截取第一个和大于第4个字
                return "**"+name.substring(2,4);
            }else if(name.length()>4){
                //截取第一个和大于第4个字
                return name.substring(0,2)+"*"+'*'+name.substring(name.length()-1);
            }
        }
        return name;
    }
}
