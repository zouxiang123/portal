package com.xtt.platform.util.secret;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import com.xtt.platform.util.security.MD5Util;
import com.xtt.platform.util.time.DateFormatUtil;

public class HardWareUtil {

    private final static String CIPHER_KEY = "xtt-HDIS";

    /**
     * 获取主板序列号
     * 
     * @return
     */
    public static String getMotherboardSN() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n" + "Set colItems = objWMIService.ExecQuery _ \n"
                            + "   (\"Select * from Win32_BaseBoard\") \n" + "For Each objItem in colItems \n"
                            + "    Wscript.Echo objItem.SerialNumber \n" + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /**
     * 获取硬盘序列号
     * 
     * @param drive
     *            盘符
     * @return
     */
    public static String getHardDiskSN(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n" + "Set colDrives = objFSO.Drives\n"
                            + "Set objDrive = colDrives.item(\"" + drive + "\")\n" + "Wscript.Echo objDrive.SerialNumber"; // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /**
     * 获取CPU序列号
     * 
     * @return
     */
    public static String getCPUSerial() {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n" + "Set colItems = objWMIService.ExecQuery _ \n"
                            + "   (\"Select * from Win32_Processor\") \n" + "For Each objItem in colItems \n"
                            + "    Wscript.Echo objItem.ProcessorId \n" + "    exit for  ' do the first cpu only! \n" + "Next \n";

            // + " exit for \r\n" + "Next";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
            file.delete();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        if (result.trim().length() < 1 || result == null) {
            result = "无CPU_ID被读取";
        }
        return result.trim();
    }

    /**
     * 获取MAC地址
     */
    public static String getMac() {
        String result = "";
        try {
            // System.out.println(System.getProperty("file.encoding"));
            // System.getProperties().list(System.out);
            Process process = Runtime.getRuntime().exec("ipconfig /all");
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            while ((line = input.readLine()) != null) {
                line = new String(line.getBytes(), "GBK");
                if (line.indexOf("Physical Address") >= 0 || line.indexOf("物理地址") >= 0) {
                    String MACAddr = line.substring(line.indexOf("-") - 2);
                    result = MACAddr;
                }
            }
        } catch (java.io.IOException e) {
            System.err.println("IOException " + e.getMessage());
        }
        return result;
    }

    private static String hexByte(byte b) {
        String s = "000000" + Integer.toHexString(b).toUpperCase();
        return s.substring(s.length() - 2);
    }

    /**
     * 根据IP地址获取mac地址
     * 
     * @param ipAddress
     *            127.0.0.1
     * @return
     * @throws SocketException
     * @throws UnknownHostException
     */
    public static String getLocalMac() {
        String macAddress = "";
        try {
            Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
            List<String> rs = new ArrayList<String>();
            while (el.hasMoreElements()) {
                byte[] mac = el.nextElement().getHardwareAddress();
                if (mac == null || mac.length == 0)
                    continue;
                StringBuilder builder = new StringBuilder();
                for (byte b : mac) {
                    builder.append(hexByte(b));
                    builder.append('-');
                }
                rs.add(builder.deleteCharAt(builder.length() - 1).toString());
            }
            Collections.sort(rs);
            String[] macs = rs.toArray(new String[] {});
            for (String strMac : macs) {
                macAddress += "\n" + strMac;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return macAddress;
    }

    public static String generateMachineCode() {

        String info = "MotherboardSN=" + getMotherboardSN() + "\n";
        info += "HardDiskSN=" + getHardDiskSN("c");
        // info += "MAC=" + getLocalMac();

        return info;
    }

    public static void createMachineCodeFile() {
        BufferedWriter writer = null;
        try {
            System.out.println("抓取信息开始...");
            File file = new File("c:/xtt");
            if (!file.exists()) {
                file.mkdirs();
            }
            writer = new BufferedWriter(new FileWriter("c:/xtt/machine_data.txt"));
            writer.append(AESUtil.encrypt(generateMachineCode(), CIPHER_KEY));
            writer.flush();// 需要及时清掉流的缓冲区，万一文件过大就有可能无法写入了
            System.out.println("文件生成结束...");
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static boolean validateLicense() {

        String machineCode = MD5Util.md5(generateMachineCode());

        BufferedReader br = null;
        try {
            String filePath = System.getProperty("catalina.home") + "/conf/license.key";
            InputStream is = new FileInputStream(new File(filePath));// HardWareUtil.class.getResourceAsStream("/config/license.key");
            br = new BufferedReader(new InputStreamReader(is));
            String lics[] = null;
            String temp = null;
            while ((temp = br.readLine()) != null) {
                lics = temp.split("=");
                if (lics[0].equals(machineCode)) {
                    Date validDate = DateFormatUtil.convertStrToDate(AESUtil.decrypt(lics[1], CIPHER_KEY), "yyyy-MM-dd HH:mm:ss");
                    if (validDate.compareTo(new Date()) > -1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}