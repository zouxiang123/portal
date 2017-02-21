package com.xtt.platform.framework.exception;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 
 * @ClassName: RDPException
 * @author: Tik
 * @CreateDate: 2014-3-28 下午2:09:53
 * @UpdateUser: Tik
 * @UpdateDate: 2014-3-28 下午2:09:53
 * @UpdateRemark: 说明本次修改内容
 * @Description: 平台Exception封装后的Exception
 * @version: V1.0
 */
public class RDPException extends RdpSupperException {
    private Map para_map = new HashMap();

    /**
     * Constructor method.
     */
    public RDPException() {
        super();
    }

    /**
     * Constructor method.
     *
     * @param msg
     *            String
     */
    public RDPException(final String msg) {
        super(msg);
    }

    /**
     * RDPException
     *
     * @param msg
     *            String
     * @param infoCode
     *            String
     */
    public RDPException(final String msg, final String infoCode) {
        super(msg);
        setInfo_Code(infoCode);
    }

    public RDPException(final String msg, final String infoCode, final Map paras) {
        super(msg);
        setInfo_Code(infoCode);
        setAllParameters(paras);
    }

    /**
     * Constructor method.
     *
     * @param msg
     *            String
     * @param nested
     *            Throwable
     */
    public RDPException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    /**
     * Constructor method.
     *
     * @param nested
     *            Throwable
     */
    public RDPException(final Throwable nested) {
        super(nested);

    }

    /**
     * set all parameters
     *
     * @param paras
     *            Map
     */
    public void setAllParameters(Map paras) {
        para_map.putAll(paras);
    }

    /**
     * get all parameters
     *
     * @return Map
     */
    public Map getAllParameters() {
        return para_map;
    }

    public void setParameter(String key, Object val) {
        para_map.put(key, val);
    }

    public Object getParameter(String key) {
        return para_map.get(key);
    }

    public Object getParameter(long index) {
        Iterator it = para_map.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (i == index)
                return para_map.get(it.next());
            it.next();
        }
        return null;
    }

}
