package com.xtt.platform.util.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @ClassName: IOUtil
 * @author: Tik
 * @CreateDate: 2014-3-28 下午5:47:43
 * @UpdateUser: Tik
 * @UpdateDate: 2014-3-28 下午5:47:43
 * @UpdateRemark: 说明本次修改内容
 * @Description: IO输出封装
 * @version: V1.0
 */
public class IOUtil {
    private static final int BUF_SIZE = 8192;

    /**
     * 判断两个输入流是否严格相等
     */
    public static boolean equals(InputStream sA, InputStream sB) throws IOException {
        int dA;
        while ((dA = sA.read()) != -1) {
            if (dA != sB.read())
                return false;
        }
        return sB.read() == -1;
    }

    /**
     * 关闭一个可关闭对象，可以接受 null。如果成功关闭，返回 true，发生异常 返回 false
     * 
     * @param cb
     *            可关闭对象
     * @return 是否成功关闭
     */
    public static boolean close(Closeable cb) {
        if (null != cb)
            try {
                cb.close();
            } catch (IOException e) {
                return false;
            }
        return true;
    }

    public static void flush(Flushable fa) {
        if (null != fa)
            try {
                fa.flush();
            } catch (IOException e) {
            }
    }
}
