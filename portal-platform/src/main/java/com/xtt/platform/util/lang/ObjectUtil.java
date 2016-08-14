package com.xtt.platform.util.lang;

import org.apache.commons.lang3.ObjectUtils;

import com.xtt.platform.framework.exception.RDPException;

public class ObjectUtil extends ObjectUtils{
	/**
     * <p>Returns a default value if the object passed is {@code null}.</p>
     *
     * <pre>
     * ObjectUtil.isNull(null)= true
     * ObjectUtils.isNull("")= true
     * ObjectUtils.isNull("ddd")= false
     * </pre>
     * @param object  the {@code Object} to test, may be {@code null}
     */
	public static boolean isNull(Object obj) {
		if(null==obj){
			return true;
		}
		return false;
	}
	
	/**
     * <p>Returns a default value if the object passed is {@code null}.</p>
     *
     * <pre>
     * ObjectUtil.isNOtNull(null)= false
     * ObjectUtils.isNOtNull("")= false
     * ObjectUtils.isNOtNull("ddd")= true
     * </pre>
     * @param object  the {@code Object} to test, may be {@code null}
     */
	public static boolean isNotNull(Object obj) {
		if(null==obj){
			return false;
		}
		return true;
	}
}
