/**   
 * @Title: PrimaryKeyUtil.java 
 * @Package com.xtt.platform.util
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2016年6月16日 下午12:49:10 
 *
 */
package com.xtt.platform.util;

import java.sql.SQLException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xtt.platform.util.time.DateFormatUtil;

public class ChargeUtil {
    private static Logger log = LoggerFactory.getLogger(ChargeUtil.class);

    /**
     * 获取收费序列
     * 
     * @Title: getSeq
     * @param date
     *            交易日期
     * @param patientId
     *            患者ID
     * @param tenantId
     *            租户ID
     * @return
     *
     */
    public static synchronized Long getSeq(Date date, Long patientId, Integer tenantId) {
        try {
            Long maxVal = 1L;
            StringBuffer params = new StringBuffer();
            params.append(tenantId);
            params.append(DateFormatUtil.convertDateToStr(date, "yyyyMMdd"));

            // 获取收费项目序列
            String sql = "select next_val from charge_seq where id=" + params.toString() + " and patientId=" + patientId;
            maxVal = (Long) DBUtil.getSingle(sql);

            // 判断序列是否存在，不存在插入
            if (maxVal == null) {

                // 获取小于当前日期最大一个数量值
                sql = "select next_val from charge_seq where id < " + params.toString() + " and patientId=" + patientId + " ORDER BY id DESC LIMIT 1";
                maxVal = (Long) DBUtil.getSingle(sql);

                if (maxVal == null) {
                    // 插入新数据
                    sql = "insert INTO charge_seq VALUES(" + params.toString() + "," + patientId + ",1)";
                    DBUtil.insertWithReturnPrimeKey(sql);
                    maxVal = 1L;
                } else {
                    // 累加 插入新数据
                    maxVal = maxVal + 1;
                    sql = "insert INTO charge_seq VALUES(" + params.toString() + "," + patientId + "," + maxVal + ")";
                    DBUtil.insertWithReturnPrimeKey(sql);
                }
            } else {
                // 更新
                maxVal = maxVal + 1;
                sql = "update charge_seq set next_val = " + maxVal + " where id = " + params.toString() + " and patientId=" + patientId;
                DBUtil.update(sql);
            }

            // 更新大于指定id的所有记录
            sql = "update charge_seq set next_val = next_val+1 where id > " + params.toString() + " and patientId=" + patientId;
            DBUtil.update(sql);

            // 更新交易记录序号
            /* sql = "update transaction_record set charge_seq = charge_seq + 1 WHERE charge_seq>=" + (maxVal) + " AND fk_patient_id = " + patientId
                            + " AND fk_tenant_id = " + tenantId;
            DBUtil.update(sql);*/

            return maxVal;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Charge Seq ERROR!");
        }
        return null;
    }
}
