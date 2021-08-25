package com.yuantu.demo.web.config.strDateConfig;


import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * String日期格式转Date
 *
 */
public class CustomDateConverter implements Converter<String, Date> {

    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String shortDateFormat = "yyyy-MM-dd";

    @Override
    public Date convert(String value) {
        if(StringUtils.isBlank(value)) {
            return null;
        }
        value = value.trim();
        // 在这里面将String转换date
        try {
            if(value.contains("-")) {
                SimpleDateFormat formatter;
                if(value.contains(":")) {
                    formatter = new SimpleDateFormat(dateFormat);
                }else {
                    formatter = new SimpleDateFormat(shortDateFormat);
                }
                Date dtDate = formatter.parse(value);
                return dtDate;
            }else if(value.matches("^\\d+$")) {
                Long lDate = new Long(value);
                return new Date(lDate);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser {} to Date fail", value));
        }
        throw new RuntimeException(String.format("parser {} to Date fail", value));
    }

}
