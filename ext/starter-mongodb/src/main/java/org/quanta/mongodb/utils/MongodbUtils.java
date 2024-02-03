package org.quanta.mongodb.utils;

import org.quanta.mongodb.entity.MongoPage;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Description: Mongodb工具类
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/28
 */
public interface MongodbUtils {
    /**
     * 保存单个对象
     *
     * @param t 要保存的对象
     * @return 保存后的对象
     */
    <T> T save(T t);

    /**
     * 将对象保存到指定的集合中
     *
     * @param objectToSave   要保存的对象
     * @param collectionName 集合名称
     * @return 保存后的对象
     */
    <T> T save(T objectToSave, String collectionName);

    /**
     * 批量保存数据
     *
     * @param list           要保存的对象集合
     * @param collectionName 集合名称
     * @return 保存后的对象集合
     */
    <T> Collection<T> batchSave(Collection<T> list, String collectionName);

    /**
     * 查询数据
     *
     * @param query  查询条件
     * @param tClass 结果对象的类型
     * @return 查询结果集合
     */
    <T> List<T> find(Query query, Class<T> tClass);

    /**
     * 在指定集合中查询数据
     *
     * @param query          查询条件
     * @param tClass         结果对象的类型
     * @param collectionName 集合名称
     * @return 查询结果集合
     */
    <T> List<T> find(Query query, Class<T> tClass, String collectionName);

    /**
     * 分页查询
     *
     * @param query          查询条件
     * @param pageNum        当前页码
     * @param pageSize       每页条目数
     * @param sortField      排序字段
     * @param sortType       1:升序；0:降序
     * @param tClass         结果对象的类型
     * @param collectionName 集合名称
     * @return 分页结果
     */
    <T> MongoPage findByPage(Query query, int pageNum, int pageSize, String sortField, int sortType, Class<T> tClass, String collectionName);

    /**
     * 查询前几条数据
     *
     * @param query          查询条件
     * @param limitNum       前几条
     * @param sortField      排序字段
     * @param sortType       0:倒序；1:正序
     * @param tClass         结果对象的类型
     * @param collectionName 集合名称
     * @return 查询结果集合
     */
    <T> List<T> findTop(Query query, Integer limitNum, String sortField, int sortType, Class<T> tClass, String collectionName);

    /**
     * 查询一条数据
     *
     * @param query          查询条件
     * @param collectionName 集合名称
     * @return 查询结果集合
     */
    <T> List<T> findOne(Query query, Class<T> tClass, String collectionName);

    /**
     * 查询一条数据(附带排序)
     *
     * @param query          查询条件
     * @param sortField      排序字段
     * @param sortType       0:倒序；1:正序
     * @param tClass         结果对象的类型
     * @param collectionName 集合名称
     * @return 查询结果集合
     */
    <T> List<T> findOne(Query query, String sortField, int sortType, Class<T> tClass, String collectionName);

    /**
     * 查询所有数据
     *
     * @param tClass 结果对象的类型
     * @return 查询结果集合
     */
    <T> List<T> findAll(Class<T> tClass);

    /**
     * 查询所有指定集合的数据
     *
     * @param tClass         结果对象的类型
     * @param collectionName 集合名称
     * @return 查询结果集合
     */
    <T> List<T> findAll(Class<T> tClass, String collectionName);

    /**
     * 创建集合
     *
     * @param collName  集合名称
     * @param indexList 索引列表
     * @return 是否创建成功
     */
    boolean createCollection(String collName, List<Map<String, Integer>> indexList);

    /**
     * 更新查询结果的第一条记录
     *
     * @param query          查询条件
     * @param update         更新操作
     * @param collectionName 集合名称
     * @return 是否更新成功
     */
    boolean updateFirst(Query query, Update update, String collectionName);

    /**
     * 更新所有查询结果的记录
     *
     * @param query          查询条件
     * @param update         更新操作
     * @param collectionName 集合名称
     * @return 是否更新成功
     */
    boolean updateMulti(Query query, Update update, String collectionName);

    /**
     * 如果更新对象不存在，则进行插入操作
     *
     * @param query          查询条件
     * @param update         更新操作
     * @param tClass         结果对象的类型
     * @param collectionName 集合名称
     * @return 是否更新成功
     */
    <T> boolean upsert(Query query, Update update, Class<T> tClass, String collectionName);

    /**
     * 存在则更新，不存在则创建
     *
     * @param query          查询条件
     * @param update         更新操作
     * @param collectionName 集合名称
     * @return 是否更新成功
     */
    boolean upsert(Query query, Update update, String collectionName);

    /**
     * 聚合查询
     *
     * @param aggregation    聚合条件
     * @param tClass         结果对象的类型
     * @param collectionName 集合名称
     * @return 查询结果集合
     */
    <T> List<T> groupQuery(Aggregation aggregation, Class<T> tClass, String collectionName);

    /**
     * 查询集合中符合条件的记录数量
     *
     * @param query          查询条件
     * @param collectionName 集合名称
     * @return 符合条件的记录数量
     */
    long queryCount(Query query, String collectionName);
}
