package com.xtt.platform.util.resource;

/**
 * 
 * @ClassName: JarEntryInfo
 * @author: Tik
 * @CreateDate: 2014-3-28 下午6:01:00
 * @UpdateUser: Tik
 * @UpdateDate: 2014-3-28 下午6:01:00
 * @UpdateRemark: 说明本次修改内容
 * @Description: jar 实体封装
 * @version: V1.0
 */
public class JarEntryInfo {

    private String jarPath;

    private String entryName;

    public JarEntryInfo() {
    }

    /**
     * 从一个包含 jar 的全路径里，截取 jar 文件的路径，以及 Entry的path。
     * <p>
     * Mac / Linux / Windows jar 文件的路径的分隔符号略有不同
     * <ul>
     * <li><b>Windows</b> - file:\D:\a\b\c\xyz.jar!\m\n\T.class
     * </ul>
     * 
     * @param path
     *            文件全路径
     * 
     * @throws IOException
     */
    public JarEntryInfo(String path) {
        path = path.replace('\\', '/');
        int posL = path.indexOf("file:");
        posL = posL < 0 ? 0 : posL + "file:".length();
        int posR = path.indexOf(".jar!") + ".jar!".length();
        this.jarPath = path.substring(posL, posR - 1);
        this.entryName = path.substring(posR + 1);
    }

    public JarEntryInfo(String jarPath, String entryName) {
        this.jarPath = jarPath;
        this.entryName = entryName;
    }

    public String getJarPath() {
        return jarPath;
    }

    public JarEntryInfo setJarPath(String jarPath) {
        this.jarPath = jarPath;
        return this;
    }

    public String getEntryName() {
        return entryName;
    }

    public JarEntryInfo setEntryName(String entryName) {
        this.entryName = entryName;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s!/%s", jarPath, entryName);
    }

}
