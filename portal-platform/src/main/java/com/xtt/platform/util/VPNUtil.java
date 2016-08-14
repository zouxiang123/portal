package com.xtt.platform.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.xtt.platform.util.config.PropertiesUtil;

public class VPNUtil
{
    private static Logger log = Logger.getLogger(VPNUtil.class);
    
    private synchronized static String executeCmd(String cmd)
    {
        try
        {
            Process process = Runtime.getRuntime().exec("cmd /c " + cmd);
            StringBuilder sbCmd = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                sbCmd.append(line);
                log.info("状态:" + line);
            }
            return sbCmd.toString();
        } catch (IOException e)
        {
            log.error("");
            return "";
        }
    }
  
    public synchronized static boolean disconnectVPN(String vpnName)
    {
        String cmd = "rasdial " + vpnName + " /disconnect";
        log.info("断开连接开始");
        String result = executeCmd(cmd);

        if (result == null || result.contains("没有连接"))
            return false;
        else return true;
    }
    public synchronized static boolean disconnectVPN()
    {
        try {
            Properties props = PropertiesUtil.loadJarProperties("/config/config.properties");
            return disconnectVPN(props.get("vpn.vpnName").toString());
        } catch (Exception e) {
            log.error("VPN参数未配置:" + e);
            return false;
        }
    }
    public synchronized static boolean connectVPN(String vpnName, String username, String password)
    {
        String cmd = "rasdial " + vpnName + " " + username + " " + password;  
        log.info("连接开始");
        String result = executeCmd(cmd);
        if (result == null || (!result.contains("已连接") && !result.contains("Successfully connected") && !result.contains("already connected")))
        {
            return false;
        }
        return true;
    }  
    
    public synchronized static boolean connectVPN()
    {
        String vpnName = null;
        String username = null;
        String password = null;
        try {
            Properties props = PropertiesUtil.loadJarProperties("/config/config.properties");
            vpnName = props.get("vpn.vpnName").toString();
            username = props.get("vpn.username").toString();
            password = props.get("vpn.password").toString();
        } catch (Exception e) {
            log.error("VPN参数未配置:" + CommonUtil.getExceptionMessage(e));
            return false;
        }
        for (int i = 0; i < 10; i++) {
            if (connectVPN(vpnName, username, password)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception
    {
//        for (int i = 0; i < 10; i++) {
//            if (connectVPN("linode","ninfo","gfw-gotohell8964")) break;
//        }
//        connectVPN();
//        disconnectVPN("linode");
        Properties props = PropertiesUtil.loadJarProperties("/config/config.properties");
        String vpnName = props.get("vpn.vpnName").toString();
        System.out.println(vpnName);
        
    }
  
}  