package com.xtt.platform.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;

/**
 * 解析SQL
 * 
 * @ClassName: ParseSqlUtil
 * @date: 2018年7月3日 上午11:19:17
 * @version: V1.0
 *
 */
public class ParseSqlUtil {

    SelectItem si = new SelectItem() {
        @Override
        public void accept(SelectItemVisitor selectItemVisitor) {
        }

        @Override
        public String toString() {
            return "1";
        }
    };

    /**
     * 解析SQL，替换Column为1
     * 
     * @Title: processColumn
     * @param sql
     * @return
     *
     */
    public String processColumn(String sql) {
    	if(sql.indexOf("distinct")!=-1) {
    		return sql;
    	}
        Statement stmt = null;
        try {
            stmt = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            // 解析未成功
            return sql;
        }
        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = ((PlainSelect) selectBody);
        List<SelectItem> ls = new ArrayList<SelectItem>();
        ls.add(si);
        plainSelect.setSelectItems(ls);
        return select.toString();
    }

    /**
     * 移除Order By xxx
     * 
     * @Title: removeOrderBy
     * @param sql
     * @return
     *
     */
    public String removeOrderBy(String sql) {
        Statement stmt = null;
        try {
            stmt = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            // 解析未成功
            return sql;
        }
        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();
        processSelectBody(selectBody);
        return select.toString();
    }

    /**
     * 解析Sql主体
     * 
     * @Title: processSelectBody
     * @param selectBody
     *
     */
    public void processSelectBody(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            processPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            if (withItem.getSelectBody() != null) {
                processSelectBody(withItem.getSelectBody());
            }
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            if (operationList.getPlainSelects() != null && operationList.getPlainSelects().size() > 0) {
                List<PlainSelect> plainSelects = operationList.getPlainSelects();
                for (PlainSelect plainSelect : plainSelects) {
                    processPlainSelect(plainSelect);
                }
            }
            if (!orderByHashParameters(operationList.getOrderByElements())) {
                operationList.setOrderByElements(null);
            }
        }
    }

    /***
     * 解析查询类别
     * 
     * @Title: processPlainSelect
     * @param plainSelect
     *
     */
    public void processPlainSelect(PlainSelect plainSelect) {
        if (!orderByHashParameters(plainSelect.getOrderByElements())) {
            plainSelect.setOrderByElements(null);
        }
        if (plainSelect.getFromItem() != null) {
            processFromItem(plainSelect.getFromItem());
        }
        if (plainSelect.getJoins() != null && plainSelect.getJoins().size() > 0) {
            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                if (join.getRightItem() != null) {
                    processFromItem(join.getRightItem());
                }
            }
        }
    }

    /**
     * 解析From表
     * 
     * @Title: processFromItem
     * @param fromItem
     *
     */
    public void processFromItem(FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoin() != null) {
                if (subJoin.getJoin().getRightItem() != null) {
                    processFromItem(subJoin.getJoin().getRightItem());
                }
            }
            if (subJoin.getLeft() != null) {
                processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {

        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = (SubSelect) (lateralSubSelect.getSubSelect());
                if (subSelect.getSelectBody() != null) {
                    processSelectBody(subSelect.getSelectBody());
                }
            }
        }
    }

    public boolean orderByHashParameters(List<OrderByElement> orderByElements) {
        if (orderByElements == null) {
            return false;
        }
        for (OrderByElement orderByElement : orderByElements) {
            if (orderByElement.toString().toUpperCase().contains("?")) {
                return true;
            }
        }
        return false;
    }
}
