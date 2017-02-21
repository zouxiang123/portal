package com.xtt.platform.framework.core.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xtt.platform.framework.exception.RDPException;

/**
 * 数据持久层基础父类
 * 
 * @ClassName: MyBatisSuperMapper
 * @author: Tik
 * @CreateDate: 2014-3-27 上午10:43:25
 * @UpdateUser: Tik
 * @UpdateDate: 2014-3-27 上午10:43:25
 * @UpdateRemark: 说明本次修改内容
 * @Description: 简单描述
 * @version: V1.0
 * @param <T>
 */
/**
 * @ClassName: MyBatisSuperMapper
 * @description: 大致描述该类的用途，内容简短易懂
 * @author: Tik
 * @date: 2014年5月12日 下午1:37:16
 * @version: V1.0
 *
 * @param <T>
 */
public interface MyBatisSuperMapper<T> {
    /**
     * 
     * @Title: findEntity
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:45:36
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:45:36
     * @UpdateRemark: 说明本次修改内容
     * @Description: 根据实体组合条件信息返回实体
     * @version: V1.0
     * @param t
     * @return
     * @throws RDPException
     */
    public T findEntity(T t) throws RDPException;

    /**
     * 
     * @Title: findEntityById
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:45:53
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:45:53
     * @UpdateRemark: 说明本次修改内容
     * @Description: 根据实体主键值以及租户ID返回实体
     * @version: V1.0
     * @param pkId
     *            实体主键
     * @param tenantId
     *            租户ID
     * @return
     * @throws RDPException
     */
    public T findEntityById(@Param("pkId") String pkId) throws RDPException;

    /**
     * 
     *
     * @Title: findEntityByStrCollection @Description:
     *         <p>
     *         根据字符集合获取实体集合，一般应用在IN下,返回 一个实体集合
     *         <p>
     * 
     *         <pre>
     *  List xlist=new ArrayList(); xlist.add("jon");
     * xlist.add("jon1"); xlist.add("jon2");
     * 
     * List <Object> ob=service.findEntityByInStr(xlist,"8001");
     * 
     * 数据库层面一般应用在IN的查询之下
     *         </pre>
     * 
     *         @param:
     *         <p>
     *         @param str_value @param:
     *         <p>
     *         @param tenantId @param:
     *         <p>
     *         @return @param:
     *         <p>
     *         @throws RDPException
     *         <p>
     *         @date: 2014年5月12日 @return: List<T> @throws
     *
     */
    public List<T> findEntityByInStr(@Param("str_value") List<Object> str_value) throws RDPException;

    /**
     * 
     *
     * @Title: findCollectionByInStr @Description:
     *         <p>
     *         根据字符集合获取实体集合，一般应用在IN下,返回的是一个数组集合
     *         <p>
     * 
     *         <pre>
     *         List xlist = new ArrayList();
     *         xlist.add("jon");
     *         xlist.add("jon1");
     *         xlist.add("jon2");
     *         List<Map<Object, Object>> ob = service.findCollectionByInStr(xlist, "8001");
     *         </pre>
     * 
     *         @param:
     *         <p>
     *         @param str_value @param:
     *         <p>
     *         @param tenantId @param:
     *         <p>
     *         @return @param:
     *         <p>
     *         @throws RDPException
     *         <p>
     *         @date: 2014年5月12日 @return: List<Map<Object,Object>> @throws
     *
     */
    public List<Map<Object, Object>> findCollectionByInStr(@Param("str_value") List<Object> str_value) throws RDPException;

    /**
     * 
     *
     * @Title: findEntityByInStrAndAnykeyName @Description:
     *         <p>
     *         根据任意列来通过IN关键字获取数据信息,返回的是一个具体的实体类
     *         <p>
     * 
     *         <pre>
     *         List xlist = new ArrayList();
     *         xlist.add("jon");
     *         xlist.add("jon1");
     *         xlist.add("jon2");
     * 
     *         List<Object> ob = service.findEntityByInStrAndAnykeyName("username", xlist, "8001"); // 在mapper配置的xml文件中通过IF任意判断来获取不同的sql执行 List <Object>
     *         ob = service.findEntityByInStrAndAnykeyName("keyId", xlist, "8001");
     *         </pre>
     * 
     *         @param:
     *         <p>
     *         @param key_name 列名 @param:
     *         <p>
     *         @param str_value 集合值 @param:
     *         <p>
     *         @param tenantId @param:
     *         <p>
     *         @return @param:
     *         <p>
     *         @throws RDPException
     *         <p>
     *         @date: 2014年5月28日 @return: List<T> @throws
     *
     */
    public List<T> findEntityByInStrAndAnykeyName(@Param("key_name") String key_name, @Param("str_value") List<Object> str_value) throws RDPException;

    /**
     * 
     *
     * @Title: findCollectionByInStrAndAnykeyName @Description:
     *         <p>
     *         根据任意列来通过IN关键字获取数据信息,返回的是一个集合信息
     *         <p>
     * 
     *         <pre>
     *  List xlist=new ArrayList();
     * xlist.add("jon"); xlist.add("jon1"); xlist.add("jon2");
     * 
     * List <Map<Object,Object>> ob=service.findCollectionByInStrAndAnykeyName("username",xlist,"8001"); //在mapper配置的xml文件中通过IF任意判断来获取不同的sql执行 List
     * <Map<Object,Object>> ob=service.findCollectionByInStrAndAnykeyName("keyId",xlist,"8001");
     *         </pre>
     * 
     *         @param:
     *         <p>
     *         @param key_name @param:
     *         <p>
     *         @param str_value @param:
     *         <p>
     *         @param tenantId @param:
     *         <p>
     *         @return @param:
     *         <p>
     *         @throws RDPException
     *         <p>
     *         @date: 2014年5月28日 @return: List<T> @throws
     *
     */
    public List<T> findCollectionByInStrAndAnykeyName(@Param("key_name") String key_name, @Param("str_value") List<Object> str_value)
                    throws RDPException;

    /**
     * 
     * @Title: findCollection
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:46:03
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:46:03
     * @UpdateRemark: 说明本次修改内容
     * @Description: 根据实体组合条件返回非实体集合信息
     * @version: V1.0
     * @param t
     * @return
     * @throws RDPException
     */
    public List<Map<Object, Object>> findCollection(T t) throws RDPException;

    /**
     * 
     * @Title: findEntityCollection
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:46:31
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:46:31
     * @UpdateRemark: 说明本次修改内容
     * @Description: 根据实体组合条件返回实体集合信息
     * @version: V1.0
     * @param t
     * @return
     * @throws RDPException
     */
    public List<T> findEntityCollection(T t) throws RDPException;

    /**
     * 
     * @Title: findEntityCollectionByMap
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:47:18
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:47:18
     * @UpdateRemark: 说明本次修改内容
     * @Description: 根据Map组合条件返回实体集合信息
     * @version: V1.0
     * @param map
     * @return
     * @throws RDPException
     */
    public List<T> findEntityCollectionByMap(Map<String, Object> map) throws RDPException;

    /**
     * 
     * @Title: findCollectionsByMap
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:47:32
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:47:32
     * @UpdateRemark: 说明本次修改内容
     * @Description: 根据Map组合条件返回非实体集合信息
     * @version: V1.0
     * @param map
     * @return
     * @throws RDPException
     */
    public List<Map<Object, Object>> findCollectionsByMap(Map<String, Object> map) throws RDPException;

    /***
     * 
     * @Title: findPageEntityCollection
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:47:42
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:47:42
     * @UpdateRemark: 说明本次修改内容
     * @Description: 获取带有分页数据集合,以实体为结果集
     * @version: V1.0
     * @param t
     * @return
     * @throws RDPException
     */
    public List<T> findPageEntityCollection(T t) throws RDPException;

    /**
     * 
     * @Title: findPageMapCollection
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:47:51
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:47:51
     * @UpdateRemark: 说明本次修改内容
     * @Description: 获取带有分页数据集合,以Map对象为结果集
     * @version: V1.0
     * @param t
     * @return
     * @throws RDPException
     */
    public List<Map<Object, Object>> findPageMapCollection(T t) throws RDPException;

    /**
     * 
     * @Title: saveEntity
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:47:59
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:47:59
     * @UpdateRemark: 说明本次修改内容
     * @Description: 插入实体
     * @version: V1.0
     * @param t
     * @throws RDPException
     */
    public void saveEntity(T t) throws RDPException;

    /**
     * 
     * @Title: saveEntityByMap
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:48:07
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:48:07
     * @UpdateRemark: 说明本次修改内容
     * @Description: 根据map信息插入实体
     * @version: V1.0
     * @param map
     * @throws RDPException
     */
    public void saveEntityByMap(Map<String, Object> map) throws RDPException;

    /**
     * 
     *
     * @Title: saveBatch @Description:
     *         <p>
     *         批量插入实体信息
     *         <p>
     * 
     *         <pre>
     *  这里描述这个方法的使用方法 – 可选
     *         </pre>
     * 
     *         @param:
     *         <p>
     *         @param list 集合 @param
     *         <p>
     *         tenantId 租户ID @param:
     *         <p>
     *         @throws RDPException
     *         <p>
     *         @date: 2014年5月23日 @return: void @throws
     *
     */
    public void saveBatch(@Param("list") List<?> list) throws RDPException;

    /**
     * 
     * @Title: updateEntity
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:48:14
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:48:14
     * @UpdateRemark: 说明本次修改内容
     * @Description: 更新实体
     * @version: V1.0
     * @param t
     * @throws RDPException
     */
    public void updateEntity(T t) throws RDPException;

    /**
     * 
     * @Title: updateEntityByMap
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:48:23
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:48:23
     * @UpdateRemark: 说明本次修改内容
     * @Description: 根据map信息更新实体
     * @version: V1.0
     * @param map
     * @throws RDPException
     */
    public void updateEntityByMap(Map<String, Object> map) throws RDPException;

    /**
     * 
     * @Title: deleteEntity
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:48:30
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:48:30
     * @UpdateRemark: 说明本次修改内容
     * @Description: 根据实体信息删除实体
     * @version: V1.0
     * @param t
     * @throws RDPException
     */
    public void deleteEntity(T t) throws RDPException;

    /**
     * 
     * @Title: deleteEntityByMap
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:48:38
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:48:38
     * @UpdateRemark: 说明本次修改内容
     * @Description: 根据map集合信息删除数据
     * @version: V1.0
     * @param map
     * @throws RDPException
     */
    public void deleteEntityByMap(Map<String, Object> map) throws RDPException;

    /**
     * 
     * @Title: deleteEntityByPkId
     * @author: Tik
     * @CreateDate: 2014-3-28 下午2:48:47
     * @UpdateUser: Tik
     * @UpdateDate: 2014-3-28 下午2:48:47
     * @UpdateRemark: 说明本次修改内容
     * @Description: 根据主键删除信息数据
     * @version: V1.0
     * @param pkId
     *            实体主键
     * @param tenantId
     *            租户Id
     * @throws RDPException
     */
    public void deleteEntityByPkId(@Param("pkId") String pkId) throws RDPException;
}
