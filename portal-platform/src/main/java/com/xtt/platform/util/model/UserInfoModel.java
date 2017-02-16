/**   
 * @Title: SecurityUserInfo.java 
 * @Package com.xtt.platform.security.controller.shiro 
 * @Description: 用户基本信息
 * Copyright: Copyright (c) 2014 
 * Company:Tik team by iss
 * @author: QUINN   
 * @date: 2014年5月19日 上午9:45:49 
 * @version: V1.0
 * update Release(文件修正记录)
 * <pre>
 * author--updateDate--description----------------------Flag————
 * Tik    2014-5-1    测试codesyle                      #Tik001
 *
 *
 *
 * </pre>
 *
 */
package com.xtt.platform.util.model;

import java.util.ArrayList;
import java.util.List;

import com.xtt.platform.util.lang.ObjectUtil;
import com.xtt.platform.util.lang.StringUtil;

public class UserInfoModel {

    protected String tenantId; // 租户id 不变的
    protected String username; // 用户名称 可变的
    protected String loginId; // 登录名 不变
    protected String usrPk; // SEC_User表主键 不变

    protected OrgModel org;// 当前用户所属组织机构

    protected OrgModel group; // 当前登录的话务组

    protected List<GlobalModel> models; // 购买的模块

    protected List<GlobalQueue> queues; // 购买的队列

    protected String agentId;

    protected String skillId;// 技能Id

    public String getSkillId() {
        if (StringUtil.isEmpty(this.skillId)) {
            this.skillId = "1";
        }
        return skillId;
    }

    public OrgModel getGroup() {
        return group;
    }

    public OrgModel getOrg() {
        // return org;
        return new OrgModel();
    }

    public String getTenantId() {
        if (StringUtil.isEmpty(this.tenantId)) {
            this.tenantId = "8001";
        }
        return tenantId;
    }

    public String getLoginId() {
        this.loginId = "51";
        return loginId;
    }

    public String getUsrPk() {
        if (StringUtil.isEmpty(this.usrPk)) {
            this.usrPk = "1";
        }
        return usrPk;
    }

    public String getUsername() {
        this.username = "管理员";
        return username;
    }

    public List<GlobalModel> getModels() {
        if (ObjectUtil.isNull(models)) {
            List<GlobalModel> xlist = new ArrayList<GlobalModel>();
            GlobalModel gm = new GlobalModel();
            gm.setId("1");
            gm.setName("IB");

            GlobalModel gm1 = new GlobalModel();
            gm1.setId("2");
            gm1.setName("OB");
            xlist.add(gm);
            xlist.add(gm1);
            return xlist;
        }
        return models;
    }

    public List<GlobalQueue> getQueues() {
        if (ObjectUtil.isNull(queues)) {
            List<GlobalQueue> xlist = new ArrayList<GlobalQueue>();
            GlobalQueue gm = new GlobalQueue();
            gm.setId("1");
            gm.setQname("投诉");

            GlobalQueue gm1 = new GlobalQueue();
            gm1.setId("2");
            gm1.setQname("建议");

            GlobalQueue gm3 = new GlobalQueue();
            gm3.setId("3");
            gm3.setQname("售后");

            xlist.add(gm);
            xlist.add(gm1);
            xlist.add(gm3);
            return xlist;
        }
        return queues;
    }

    public String getAgentId() {
        return agentId;
    }

}
