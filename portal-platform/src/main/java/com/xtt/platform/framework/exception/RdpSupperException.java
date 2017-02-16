package com.xtt.platform.framework.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 
 * @ClassName: RdpSupperException
 * @author: Tik
 * @CreateDate: 2014-3-28 下午2:15:25
 * @UpdateUser: Tik
 * @UpdateDate: 2014-3-28 下午2:15:25
 * @UpdateRemark: 说明本次修改内容
 * @Description: 基础异常信息包
 * @version: V1.0
 */
public class RdpSupperException extends Exception {
    // ERROR_CODE, map table: DEF_INFO_CODE.Info_Code
    public String Info_Code;
    /** The throwable throwable */
    protected final Throwable throwable;

    public void setInfo_Code(String Info_Code) {
        this.Info_Code = Info_Code;
    }

    /**
     * Construct a <tt>throwableException</tt> with the specified detail message.
     *
     * @param msg
     *            Detail message.
     */
    public RdpSupperException(final String msg) {
        super(msg);
        this.throwable = null;
    }

    /**
     * Construct a <tt>throwableException</tt> with the specified detail message and throwable <tt>Throwable</tt>.
     *
     * @param msg
     *            Detail message.
     * @param throwable
     *            throwable <tt>Throwable</tt>.
     */
    public RdpSupperException(final String msg, final Throwable throwable) {
        super(msg);
        this.throwable = throwable;
    }

    /**
     * Construct a <tt>throwableException</tt> with the specified throwable <tt>Throwable</tt>.
     *
     * @param throwable
     *            throwable <tt>Throwable</tt>.
     */
    public RdpSupperException(final Throwable throwable) {
        this(throwable.getMessage(), throwable);
    }

    /**
     * Construct a <tt>throwableException</tt> with no detail.
     */
    public RdpSupperException() {
        super();
        this.throwable = null;
    }

    /**
     * Return the throwable <tt>Throwable</tt>.
     *
     * @return throwable <tt>Throwable</tt>.
     */
    public Throwable getthrowable() {
        return throwable;
    }

    public String getInfo_Code() {
        return Info_Code;
    }

    /**
     * Return the throwable <tt>Throwable</tt>.
     *
     * <p>
     * For JDK1.6 compatibility.
     *
     * @return throwable <tt>Throwable</tt>.
     */
    public Throwable getCause() {
        return throwable;
    }

    /**
     * Returns the composite throwable message.
     *
     * @return The composite throwable message.
     */
    public String getMessage() {
        String msg = super.getMessage();
        StringBuffer buff = new StringBuffer(msg == null ? "" : msg);

        if (throwable != null) {
            buff.append(msg == null ? "- " : "; - ").append("throwable throwable: (").append(throwable).append(")");
        }

        return buff.toString();
    }

    /**
     * Prints the composite message and the embedded stack trace to the specified print stream.
     *
     * @param stream
     *            Stream to print to.
     */
    public void printStackTrace(final PrintStream stream) {
        if (throwable == null) {
            super.printStackTrace(stream);
        }

        if (stream == null)
            throw new IllegalArgumentException("stream");

        if (throwable != null) {
            synchronized (stream) {
                stream.print(" + throwable throwable: ");
                throwable.printStackTrace(stream);
            }
        }
    }

    /**
     * Prints the composite message and the embedded stack trace to the specified print writer.
     *
     * @param writer
     *            Writer to print to.
     */
    public void printStackTrace(final PrintWriter writer) {
        if (throwable == null) {
            super.printStackTrace(writer);
        }
        if (writer == null)
            throw new IllegalArgumentException("writer");

        if (throwable != null) {
            synchronized (writer) {
                writer.print(" + throwable throwable: ");
                throwable.printStackTrace(writer);
            }
        }
    }

    /**
     * Prints the composite message and the embedded stack trace to <tt>System.err</tt>.
     */
    public void printStackTrace() {
        printStackTrace(System.err);
    }

}
