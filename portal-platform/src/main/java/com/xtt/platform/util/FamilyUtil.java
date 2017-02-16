package com.xtt.platform.util;

import java.util.Properties;

import com.xtt.platform.util.config.PropertiesUtil;
import com.xtt.platform.util.lang.StringUtil;

/**
 * 获取姓氏首字母
 * 
 * @author Cyw
 *
 */
public class FamilyUtil {
    private static Properties familyProp;

    /**
     * 根据姓获取姓的首字母
     * 
     * @param name
     * @return
     */
    public static String getInitial(String name) {
        if (StringUtil.isBlank(name)) {
            return name;
        }
        if (familyProp == null) {
            familyProp = PropertiesUtil.loadJarProperties("/family.properties", "utf-8");
        }
        return familyProp.get(name) == null ? SpellUtil.getSpellInitials(name) : (String) familyProp.get(name);
    }
}
