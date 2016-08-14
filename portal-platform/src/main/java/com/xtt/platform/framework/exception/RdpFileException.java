package com.xtt.platform.framework.exception;

import org.apache.commons.beanutils.PropertyUtils;
/**
 * 
 * @ClassName: RdpFileException
 * @author:  Tik 
 * @CreateDate: 2014-3-28 下午1:40:47   
 * @UpdateUser: Tik   
 * @UpdateDate: 2014-3-28 下午1:40:47   
 * @UpdateRemark: 说明本次修改内容
 * @Description:  file creat、update、set、delet Exception
 * @version: V1.0
 */
public class RdpFileException extends java.lang.RuntimeException {
	/**
	 * The default constructor for <code>RdpFileException</code>.
	 */
	public RdpFileException() {
	}

	/**
	 * Constructs a new instance of <code>RdpFileException</code>.
	 * @param throwable the parent Throwable
	 */
	public RdpFileException(Throwable throwable) {
		super(findRootCause(throwable));
	}

	/**
	 * Constructs a new instance of <code>RdpFileException</code>.
	 * @param message the throwable message.
	 */
	public RdpFileException(String message) {
		super(message);
	}

	/**
	 * Constructs a new instance of <code>RdpFileException</code>.
	 * @param message   the throwable message.
	 * @param throwable the parent of this Throwable.
	 */
	public RdpFileException(String message, Throwable throwable) {
		super(message, findRootCause(throwable));
	}

	/**
	 * Finds the root cause of the parent exception by traveling up the
	 * exception tree
	 */
	private static Throwable findRootCause(Throwable th) {
		if (th != null) {
			// Reflectively get any exception causes.
			try {
				Throwable targetException = null;
				// java.lang.reflect.InvocationTargetException
				String exceptionProperty = "targetException";
				if (PropertyUtils.isReadable(th, exceptionProperty)) {
					targetException = (Throwable) PropertyUtils.getProperty(th,
							exceptionProperty);
				} else {
					exceptionProperty = "causedByException";
					// javax.ejb.EJBException
					if (PropertyUtils.isReadable(th, exceptionProperty)) {
						targetException = (Throwable) PropertyUtils
								.getProperty(th, exceptionProperty);
					}
				}
				if (targetException != null) {
					th = targetException;
				}
			} catch (Exception ex) {
				// just print the exception and continue
				ex.printStackTrace();
			}

			if (th.getCause() != null) {
				th = th.getCause();
				th = findRootCause(th);
			}
		}
		return th;
	}
}
