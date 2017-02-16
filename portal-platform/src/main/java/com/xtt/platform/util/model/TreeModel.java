/**   
* @Title: TreeModel.java 
* @Package com.xtt.platform.util.model 
* @Description: 树模型
* Copyright: Copyright (c) 2014 
* Company:Tik team by iss
* @author: Tik   
* @date: 2014年6月4日 上午10:00:45 
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

import com.xtt.platform.framework.core.model.MyBatisSuperModel;

public class TreeModel extends MyBatisSuperModel {
    private String id;
    // 树节点id,对应数据库的实体PK
    private String name;
    // 树节点名称,对应数据库的实体业务名称
    private String pId;
    // 树节点父ID,对应数据库的实体parentId
    private int nodeCount;
    // 父节点下的子节点数目,是一个虚拟字段,通过数据库sql计算所得
    private int orderId;
    // 排序字段,对应数据库的排序字段
    private String ischeck;
    // 数据库中已经保存后的数据信息,0代表已经全选中,1代表半选中
    private boolean open;
    // 是否展开 true 展开 false 不展开

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        if (null != ischeck && !"".equals(ischeck)) {
            this.ischeck = ischeck;
        } else {
            this.ischeck = "3";
        }

    }

}
