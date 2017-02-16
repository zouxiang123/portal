package com.xtt.platform.util.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @ClassName: NullInputStream
 * @author: Tik
 * @CreateDate: 2014-3-28 下午5:50:40
 * @UpdateUser: Tik
 * @UpdateDate: 2014-3-28 下午5:50:40
 * @UpdateRemark: 说明本次修改内容
 * @Description: 空io流输出
 * @version: V1.0
 */
public class NullInputStream extends InputStream {

    public int read() throws IOException {
        return -1;
    }

}
