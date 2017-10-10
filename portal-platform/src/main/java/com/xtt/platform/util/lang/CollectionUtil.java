package com.xtt.platform.util.lang;

import java.util.Collection;

import org.apache.commons.collections.ListUtils;

/**
 * 
 * @ClassName: CollectionUtil
 * @author: Tik
 * @CreateDate: 2014-3-28 下午10:43:33
 * @UpdateUser: Tik
 * @UpdateDate: 2014-3-28 下午10:43:33
 * @UpdateRemark: 说明本次修改内容
 * @Description: 集合工具
 * @version: V1.0
 */
public class CollectionUtil extends ListUtils {
    /**
     * 
     * @Title: isEmpty
     * @author: Tik
     *
     *          <p>
     *          check collection is empty
     *          </p>
     *
     *          <pre>
     * CollectionUtil.isEmpty(null)   = true
     * Map.put("ddd","ddd");
     * CollectionUtil.isEmpty(Map)=false
     *          </pre>
     *
     */
    public static boolean isEmpty(Collection collection) {
        return null != collection && !collection.isEmpty() ? false : true;
    }

    /**
     * 
     * @Title: isNotEmpty
     * @author: Tik
     *
     *          <p>
     *          check collection is empty
     *          </p>
     *
     *          <pre>
     * CollectionUtil.isNotEmpty(null)   = false
     * Map.put("ddd","ddd");
     * CollectionUtil.isNotEmpty(Map)=true
     *          </pre>
     *
     */
    public static boolean isNotEmpty(Collection collection) {
        return null != collection && !collection.isEmpty() ? true : false;
    }

    public static void main(String[] args) {
        System.out.println(CollectionUtil.isNotEmpty(null));
    }
}
