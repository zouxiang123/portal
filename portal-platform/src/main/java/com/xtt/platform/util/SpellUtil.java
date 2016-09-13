/**   
 * @Title: SpellUtil.java 
 * @Package com.xtt.platform.util
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2016年9月12日 下午4:34:20 
 *
 */
package com.xtt.platform.util;

import java.util.ArrayList;
import java.util.List;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * 将中文转成拼音首字母拼写
 * 
 * @ClassName: SpellUtil
 * @date: 2016年9月12日 下午4:42:28
 * @version: V1.0
 *
 */
public class SpellUtil {
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
			spellInitials += "," + s;
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

	public static void main(String[] args) {
		System.out.println(getSpellInitials("小张"));
	}
}
