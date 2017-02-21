/**   
 * @Title: NumberFormatUtil.java 
 * @Package com.xtt.platform.util.lang
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2015年11月15日 下午6:20:50 
 *
 */
package com.xtt.platform.util.lang;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class NumberFormatUtil {
    /**
     * 格式化数值
     * 
     * @Title: formatNumber
     * @param num
     * @return
     *
     */
    public static String formatNumber(Object num) {
        return formatNumber(num, true);
    }

    /**
     * 去掉小数点后面的0
     * 
     * @Title: formatNumber
     * @param num
     * @param needGroup是否需要分组
     * @return
     *
     */
    public static String formatNumber(Object num, boolean needGroup) {
        if (num == null) {
            return "";
        }
        if (num instanceof BigDecimal || num instanceof Float || num instanceof Double) {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(needGroup);
            return nf.format(num);
        }
        return num.toString();
    }
}
