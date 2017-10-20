/**   
 * @Title: PinyinUtil.java 
 * @Package com.xtt.common.util
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2017年3月21日 下午7:00:07 
 *
 */
package com.xtt.platform.util;

import java.util.ArrayList;
import java.util.List;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

public class PinyinUtil {
    /**
     * 获取字符串的中文拼音
     * 
     * @Title: getPinyin
     * @param str
     *            字符串
     * @return 字符串的拼音
     *
     */
    public static String getPinyin(String str) {
        return PinyinHelper.convertToPinyinString(str, "", PinyinFormat.WITHOUT_TONE);
    }

    /**
     * 获取字符串对应拼音的首字母
     * 
     * @Title: getShortPinyin
     * @param str
     * @return 对应拼音的首字母
     *
     */
    public static String getShortPinyin(String str) {
        return PinyinHelper.getShortPinyin(str);
    }

    /**
     * 获取拼音首字母
     * 
     * @Title: getSpellInitials
     * @param name
     * @return 多音字时，返回以,分隔的多个首字母
     *
     */
    public static String getSpellInitials(String name) {
        char[] names = name.toCharArray();
        List<List<String>> list = new ArrayList<List<String>>();
        for (char c : names) {
            String[] pinyin = PinyinHelper.convertToPinyinArray(c, PinyinFormat.WITHOUT_TONE);
            if (pinyin == null)
                continue;
            List<String> mp = new ArrayList<String>();
            for (int i = 0; i < pinyin.length; i++) {
                mp.add(pinyin[i].substring(0, 1));
            }
            list.add(mp);
        }
        List<String> rl = new ArrayList<String>();
        combine("", list, rl);
        String spellInitials = "";
        for (String s : rl) {
            if (spellInitials.indexOf(s) == -1) {// 去掉重复字符
                spellInitials += "," + s;
            }
        }
        return spellInitials.length() > 0 ? spellInitials.substring(1) : name;
    }

    /**
     * 递归获取患者所有姓名首字母(多间字）的组合。
     * 
     * @Title: combine
     * @param str
     *            组合项
     * @param list
     *            姓名首字母
     * @param rl
     *            组合结果
     * @return
     *
     */
    private static String combine(String str, List<List<String>> list, List<String> rl) {
        if (list.size() == 0)
            return "";
        List<String> curList = list.get(0);
        String temp = str;
        for (String s : curList) {
            temp = str + s;
            if (list.size() > 1) {
                combine(temp, list.subList(1, list.size()), rl);
            } else {
                rl.add(temp);
            }
        }
        return str;
    }

}
