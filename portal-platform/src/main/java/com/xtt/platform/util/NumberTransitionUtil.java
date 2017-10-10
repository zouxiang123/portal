/**   
 * @Title: Test7.java 
 * @Package com.xtt.txgl.common.util
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2015年9月24日 下午6:41:54 
 *
 */
package com.xtt.platform.util;

public class NumberTransitionUtil {
    public static void main(String[] args) {
        // 要输入的数字12
        String i = 12 + "";
        System.out.println(transition(i));
    }

    // 阿拉伯数字转中文小写？
    public static String transition(String si) {
        String str = "";

        String[] aa = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿" };
        String[] bb = { "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        char[] ch = si.toCharArray();
        int maxindex = ch.length;
        // 字符的转换
        // 两位数的特殊转换
        if (maxindex == 2) {
            for (int i = maxindex - 1, j = 0; i >= 0; i--, j++) {
                if (ch[j] != 48) {
                    if (j == 0 && ch[j] == 49) {
                        str += aa[i];
                    } else {
                        str += (bb[ch[j] - 49] + aa[i]);
                    }
                }
            }
            // 其他位数的特殊转换，使用的是int类型最大的位数为十亿
        } else {
            for (int i = maxindex - 1, j = 0; i >= 0; i--, j++) {
                if (ch[j] != 48) {
                    str += (bb[ch[j] - 49] + aa[i]);
                }
            }
        }
        return str;
    }

    /**
     * 星期转中文
     * 
     * @Title: transitionToWeek
     * @param index
     * @return （0和7返回周日）
     *
     */
    public static String transitionToWeek(int index) {
        if (index < 0 || index > 7) {
            return null;
        }
        String[] ch = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
        switch (index) {
        case 0:
        case 7:
            return ch[6];
        case 1:
            return ch[0];
        case 2:
            return ch[1];
        case 3:
            return ch[2];
        case 4:
            return ch[3];
        case 5:
            return ch[4];
        case 6:
            return ch[5];
        default:
            return null;
        }
    }

    /**
     * 月份转中文
     * 
     * @Title: transitionToMonth
     * @param index
     * @return
     *
     */
    public static String transitionToMonth(int index) {
        if (index <= 0 || index > 12) {
            return null;
        }
        String[] ch = { "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" };

        switch (index) {
        case 1:
            return ch[0];
        case 2:
            return ch[1];
        case 3:
            return ch[2];
        case 4:
            return ch[3];
        case 5:
            return ch[4];
        case 6:
            return ch[5];
        case 7:
            return ch[6];
        case 8:
            return ch[7];
        case 9:
            return ch[8];
        case 10:
            return ch[9];
        case 11:
            return ch[10];
        case 12:
            return ch[11];
        default:
            return null;

        }
    }
}
