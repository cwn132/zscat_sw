/**
 *
 */
package com.macro.mall.portal.util;


import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Title: JsonValueProcessorImpl.java</p>
 * <p>Description: net.js.json 特殊值处理</p>
 * <p>Copyright: Copyright (c) 2014-2018</p>
 * <p>Company: leimingtech.com</p>
 *
 * @author linjm
 * @version 1.0
 * @date 2015年7月17日
 */
public class JsonValueProcessorImpl implements JsonValueProcessor {

    private String format = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        String[] obj = {};
        if (value instanceof Date[]) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date[] date = (Date[]) value;
            for (int i = 0; i < date.length; i++) {
                obj[i] = sdf.format(date[i]);
            }
        }
        return obj;
    }

    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        if (value instanceof Date) {
            String str = new SimpleDateFormat(format).format(value);
            return str;
        }
        return value.toString();
    }

}
