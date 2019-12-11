package cn.isuyu.easy.pay.spring.boot.autoconfigure.utils;

import java.lang.reflect.Field;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/11 5:03 下午
 */
public class ChkParamsUtils {

    public static void requireNonNull(Object obj,String fields,String msg) {
        if (!isNullOrEmpty(fields)) {
            //验证字段非空
            String[] columns = fields.split(",");
            for (String column : columns) {
                try {
                    Field field = obj.getClass().getDeclaredField(column.trim());
                    field.setAccessible(true);
                    // 获取属性的对应的值
                    Object value = field.get(obj);
                    if (value == null || isNullOrEmpty(String.valueOf(value))) {
                        throw new NullPointerException("easy pay propertie 【easy.pay.".concat(msg).concat("."+msg.concat("."+column).concat("】 is required can not be null ")));
                    }
                    //系统异常
                } catch (NoSuchFieldException e) {

                } catch (IllegalAccessException e) {

                }
            }

        }
    }


    public static boolean isNullOrEmpty(String str) {
        return null == str || "".equals(str) || "null".equals(str);
    }

    public static boolean isNullOrEmpty(Object obj) {
        return null == obj || "".equals(obj);
    }
}
