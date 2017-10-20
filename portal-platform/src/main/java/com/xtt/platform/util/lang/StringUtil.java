package com.xtt.platform.util.lang;

import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.xtt.platform.util.time.DateUtil;

/**
 * 文本处理工具类
 * 
 * @author Tik
 * 
 */
public class StringUtil extends StringUtils {

    public static String fillLeft(String s, char c, int i) {
        return fillStr(s, c, i, true);
    }

    private static String fillStr(String s, char c, int i, boolean flag) {
        int j = i - s.length();
        if (j <= 0)
            return s;
        StringBuilder stringbuilder = new StringBuilder(s);
        for (; j > 0; j--)
            if (flag)
                stringbuilder.insert(0, c);
            else
                stringbuilder.append(c);

        return stringbuilder.toString();
    }

    /**
     * 
     * @Title: getPingYin
     * @author: Tik
     * 
     *          <p>
     *          传入城市名称,获得城市拼音全拼
     *          </p>
     * 
     *          <pre>
     * StringUtil.getPingYin("汉中")   = hanzhong
     * StringUtils.isEmptyByObject("陕西")     = shanxi
     *          </pre>
     * 
     */

    public static String getPingYin(String src) {
        // char[] t1;
        // t1 = src.toCharArray();
        // String[] t2;
        // HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        // t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // t3.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
        // String t4 = "";
        // try {
        // for (char aT1 : t1) {
        // // 判断是否为汉字字符
        // if (Character.toString(aT1).matches("[\\u4E00-\\u9FA5]+")) {
        // t2 = PinyinHelper.toHanyuPinyinStringArray(aT1, t3);
        // t4 += t2[0];
        // } else {
        // t4 += Character.toString(aT1);
        // }
        // }
        // return t4;
        // } catch (BadHanyuPinyinOutputFormatCombination e1) {
        // return "";
        // }
        return null;
    }

    /**
     * 将回车符替换为html 中的回车符
     * 
     * @Title: replaceCRToBr
     * @return
     * 
     */
    public static String replaceCRToBr(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            str = str.replace("\r\n", "<br />");// 替换windows的换行符
            str = str.replace("\r", "<br />");// 替换mac的换行符
            str = str.replace("\n", "<br />");// 替换linux的换行符
            return str;
        }
    }

    /**
     * 格式化电话号码（11位号码格式成3-4-4，12位格式成4-4-4） 例：15000831358-》150-0083-1358 055112345678-》0551-1234-5678
     * 
     * @Title: formatMobile
     * @param mobileNo
     * @return
     * 
     */
    public static String formatMobile(String mobileNo) {
        String formatNo = mobileNo;
        if (StringUtils.isNotBlank(mobileNo)) {
            int length = mobileNo.length();
            if (length == 11) {
                formatNo = mobileNo.substring(0, 3) + "-" + mobileNo.substring(3, 7) + "-" + mobileNo.substring(7);
            } else if (length == 12) {
                formatNo = mobileNo.substring(0, 4) + "-" + mobileNo.substring(4, 8) + "-" + mobileNo.substring(8);
            } else {
                formatNo = mobileNo;
            }
        }
        return formatNo;
    }

    public static boolean isNumber(String str) {
        if (isNotEmpty(str)) {
            String regex = "^(\\-)?\\d+(\\.\\d{1,4})?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher match = pattern.matcher(str);
            if (match.matches() == false) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public static String getUUIDFive() {
        String reqId = (UUID.randomUUID().toString()).substring(0, 5);
        String currentDate = DateUtil.format(new Date(), "yyyyMMdd");
        return currentDate + "_" + reqId;
    }

    /**
     * 将字符串第一个字符转为小写
     * 
     * @Title: firstToLowerCase
     * @param str
     * @return
     *
     */
    public static String firstToLowerCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        return str.substring(0, 1).toLowerCase().concat(str.substring(1));
    }

    /**
     * 将字符串第一个字符转为大写
     * 
     * @Title: firstToUpperCase
     * @param str
     * @return
     *
     */
    public static String firstToUpperCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase().concat(str.substring(1));
    }

    public static void main(String[] args) {

    }
}
