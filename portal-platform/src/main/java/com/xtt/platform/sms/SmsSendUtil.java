package com.xtt.platform.sms;

import com.xtt.platform.util.ConfigFileUtil;
import com.xtt.platform.util.security.MD5Util;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 发送短信验证码工具类
 * 
 * @author lyong
 *
 */
public class SmsSendUtil {
    private static final Logger LOG = Logger.getLogger(SmsSendUtil.class);

    /**
     * 短信平台发送短信接口地址
     */
    public static String smsUrl;

    /**
     * 短信平台用户编号
     */
    public static String uid;

    /**
     * 短信平台密钥
     */
    public static String secretKey;

    /**
     * 短信模板
     */
    public static String smsTemplate;

    /**
     * 短信验证码有效时间 (单位：分钟)
     */
    public static Integer smsValidity;

    static {

        try {
            Properties properties = new ConfigFileUtil().readProperty("/sms.properties");
            smsUrl = properties.getProperty("smsUrl");
            uid = properties.getProperty("uid");
            secretKey = properties.getProperty("secretKey");
            smsTemplate = new String(properties.getProperty("smsTemplate").getBytes("ISO-8859-1"), "utf-8");
            smsValidity = Integer.parseInt(properties.getProperty("smsValidity"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 使用POST方式发送短信验证码
     * 
     * @param mobiles
     * @param code
     * @return 如：SendSMSSuccess
     */
    public static String sendSmsPost(String mobiles, String code) {
        LOG.info("/sendSmsPost----->mobiles:" + mobiles + ",code:" + code);
        String res = null;
        try {
            String content = String.format(smsTemplate, code, smsValidity);
            String secret = encodingSecret(mobiles, content, "sms");
            String smsParam = "uid=" + uid + "&secret=" + secret + "&mobiles=" + mobiles + "&content=" + URLEncoder.encode(content, "UTF-8")
                            + "&type=sms";
            res = HttpSend.postSend(smsUrl, smsParam);
            LOG.info("/sendSmsPost----->mobiles:" + mobiles + ",content:" + content + "----->" + res);
        } catch (Exception e) {
            LOG.error(e, e);
        }
        return res;
    }

    /**
     * 使用GET方式发送短信验证码
     * 
     * @param mobiles
     * @param code
     * @return 如：SendSMSSuccess
     */
    public static String sendSmsGet(String mobiles, String code) {
        LOG.info("/sendSmsGet----->mobiles:" + mobiles + ",code:" + code);
        String res = null;
        try {
            String content = String.format(smsTemplate, code, smsValidity);
            String secret = encodingSecret(mobiles, content, "sms");
            String smsParam = "uid=" + uid + "&secret=" + secret + "&mobiles=" + mobiles + "&content=" + URLEncoder.encode(content, "UTF-8")
                            + "&type=sms";
            res = HttpSend.getSend(smsUrl, smsParam);
            LOG.info("/sendSmsGet----->mobiles:" + mobiles + ",content:" + content + "----->" + res);
        } catch (Exception e) {
            LOG.error(e, e);
        }
        return res;
    }

    /**
     * 生产消息密钥
     * 
     * @param mobiles
     * @param content
     * @param type
     * @return
     */
    private static String encodingSecret(String mobiles, String content, String type) {
        String params = uid + "," + mobiles + "," + content + "," + type + "," + secretKey;
        return MD5Util.md5(params);
    }

    /**
     * 解析出字符串中的键值对 如 "name=del&id=123"，解析出name:del,id:123存入map中
     * 
     * @param str
     *            要解析的字符串
     * @return 解析好的Map
     */
    public static Map<String, String> URLRequest(String str) {
        Map<String, String> mapRequest = new HashMap<String, String>();

        // 每个键值为一组
        String[] arrSplit = str.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    // 只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    public static void main(String[] args) {
        String res = SmsSendUtil.sendSmsPost("13585701273", "686868");
        // String res = SmsSendUtil.sendSmsGet("15221061599", "686868");
        // String res = SmsSendUtil.getBalance();

        // String res = SmsSendUtil.upPwd("HENGRONG");
        System.out.println(res);
    }
}
