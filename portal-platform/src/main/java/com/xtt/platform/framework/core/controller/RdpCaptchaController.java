package com.xtt.platform.framework.core.controller;

/**
 * 
 * @ClassName: RdpCaptchaController
 * @author: Tik
 * @CreateDate: 2014-4-4 下午1:30:50
 * @UpdateRemark: 说明本次修改内容
 * @Description: 验证码生成
 * @version: V1.0
 */
/*
@Controller
public class RdpCaptchaController extends SpringSuperController {
private Logger loger = LoggerFactory.getLogger(RdpCaptchaController.class);
@Resource(name = "captchaProducers")
private Producer captchaProducers;

*//**
   * 
   * @Title: getKaptchaImage
   * @author: Tik
   * @CreateDate: 2014-4-4 下午1:36:24
   * @UpdateRemark: 说明本次修改内容
   * @Description: 防止Captcha机器人登陆
   * @version: V1.0
   * @param request
   * @param response
   * @throws Exception
   *//*
    @RequestMapping(value = "/kaptcha-image", method = RequestMethod.GET)
    public ModelAndView getKaptchaImage(HttpServletRequest request,
    HttpServletResponse response) {
    HttpSession session = request.getSession();
    String code = (String) session
    .getAttribute(Constants.KAPTCHA_SESSION_KEY);
    loger.info("******************验证码是: " + code + "******************");
    response.setDateHeader("Expires", 0);
    // Set standard HTTP/1.1 no-cache headers.
    response.setHeader("Cache-Control",
    "no-store, no-cache, must-revalidate");
    // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
    // Set standard HTTP/1.0 no-cache header.
    response.setHeader("Pragma", "no-cache");
    // return a jpeg
    response.setContentType("image/jpeg");
    // create the text for the image
    String capText = captchaProducers.createText();
    // store the text in the session
    loger.info("最终生成的验证码为：" + capText);
    session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
    // create the image with the text
    BufferedImage bi = captchaProducers.createImage(capText);
    ServletOutputStream out = null;
    try {
    out = response.getOutputStream();
    // write the data out
    ImageIO.write(bi, "jpg", out);
    out.flush();
    } catch (Exception ex) {
    ex.printStackTrace();
    } finally {
    try {
    if (null != out) {
    out.close();
    }
    } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }
    }
    return null;
    }
    }
    */