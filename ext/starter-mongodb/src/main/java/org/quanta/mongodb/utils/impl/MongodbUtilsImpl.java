package org.quanta.mongodb.utils.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.IndexModel;
import lombok.RequiredArgsConstructor;
import org.quanta.mongodb.entity.MongoPage;
import org.quanta.mongodb.utils.MongodbUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Description: mongodb service增强
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/28
 */
@RequiredArgsConstructor
public class MongodbUtilsImpl implements MongodbUtils {
    private final MongoTemplate mongoTemplate;

    /**
     * 保存单个对象
     *
     * @param t   要保存的对象
     * @param <T> 对象的类型
     * @return 保存后的对象
     */
    public <T> T save(T t) {
        return mongoTemplate.insert(t);
    }

    /**
     * 将对象保存到指定的集合中
     *
     * @param objectToSave   要保存的对象
     * @param collectionName 集合名称
     * @param <T>            对象的类型
     * @return 保存后的对象
     */
    public <T> T save(T objectToSave, String collectionName) {
        return mongoTemplate.insert(objectToSave, collectionName);
    }

    /**
     * 批量保存数据
     *
     * @param list           要保存的对象集合
     * @param collectionName 集合名称
     * @return 保存后的对象集合
     */
    public <T> Collection<T> batchSave(Collection<T> list, String collectionName) {
        return mongoTemplate.insert(list, collectionName);
    }

    /**
     * 查询数据
     *
     * @param query  查询条件
     * @param tClass 结果对象的类型
     * @param <T>    结果对象的类型
     * @return 查询结果集合
     */
    public <T> List<T> find(Query query, Class<T> tClass) {
        return mongoTemplate.find(query, tClass);
    }

    /**
     * 在指定集合中查询数据
     *
     * @param query          查询条件
     * @param tClass         结果对象的类型
     * @param collectionName 集合名称
     * @param <T>            结果对象的类型
     * @return 查询结果集合
     */
    public <T> List<T> find(Query query, Class<T> tClass, String collectionName) {
        return mongoTemplate.find(query, tClass, collectionName);
    }

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
     * @param <T>            结果对象的类型
     * @return 分页结果
     */
    public <T> MongoPage findByPage(Query query, int pageNum, int pageSize, String sortField, int sortType, Class<T> tClass, String collectionName) {
        int count = (int) mongoTemplate.count(query, tClass, collectionName);
        if (sortType == 1) {
            query.with(Sort.by(Sort.Order.asc(sortField)));
        } else {
            query.with(Sort.by(Sort.Order.desc(sortField)));
        }
        //Set starting number
        query.skip((pageNum - 1) * pageSize);
        //Set the number of queries
        query.limit(pageSize);
        //Query the current page data set
        List<T> taskList = mongoTemplate.find(query, tClass, collectionName);
        int size = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        MongoPage page = new MongoPage();
        page.setTotal(count);
        page.setSize(size);
        page.setData(taskList);
        return page;
    }

    /**
     * 查询前几条数据
     *
     * @param query          查询条件
     * @param limitNum       前几条
     * @param sortField      排序字段
     * @param sortType       0:倒序；1:正序
     * @param tClass         结果对象的类型
     * @param collectionName 集合名称
     * @param <T>            结果对象的类型
     * @return 查询结果集合
     */
    public <T> List<T> findTop(Query query, Integer limitNum, String sortField, int sortType, Class<T> tClass, String collectionName) {
        if (sortType == 1) {
            query.with(Sort.by(Sort.Order.asc(sortField)));
        } else {
            query.with(Sort.by(Sort.Order.desc(sortField)));
        }
        query.limit(limitNum);
        return mongoTemplate.find(query, tClass, collectionName);
    }

    @Override
    public <T> List<T> findOne(Query query, Class<T> tClass, String collectionName) {
        query.limit(1);
        return mongoTemplate.find(query, tClass, collectionName);
    }

    /**
     * 查询一条数据
     *
     * @param query          查询条件
     * @param sortField      排序字段
     * @param sortType       0:倒序；1:正序
     * @param tClass         结果对象的类型
     * @param collectionName 集合名称
     * @param <T>            结果对象的类型
     * @return 查询结果集合
     */
    public <T> List<T> findOne(Query query, String sortField, int sortType, Class<T> tClass, String collectionName) {
        if (sortType == 1) {
            query.with(Sort.by(Sort.Order.asc(sortField)));
        } else {
            query.with(Sort.by(Sort.Order.desc(sortField)));
        }
        //Set the number of queries
        query.limit(1);
        //Query the current page data set
        return mongoTemplate.find(query, tClass, collectionName);
    }

    /**
     * 查询所有数据
     *
     * @param tClass 结果对象的类型
     * @param <T>    结果对象的类型
     * @return 查询结果集合
     */
    public <T> List<T> findAll(Class<T> tClass) {
        return mongoTemplate.findAll(tClass);
    }

    /**
     * 查询所有指定集合的数据
     *
     * @param tClass         结果对象的类型
     * @param collectionName 集合名称
     * @param <T>            结果对象的类型
     * @return 查询结果集合
     */
    public <T> List<T> findAll(Class<T> tClass, String collectionName) {
        return mongoTemplate.findAll(tClass, collectionName);
    }

    /**
     * 创建集合
     *
     * @param collName  集合名称
     * @param indexList 索引列表
     * @return 是否创建成功
     */
    public boolean createCollection(String collName, List<Map<String, Integer>> indexList) {
        try {
            if (mongoTemplate.collectionExists(collName)) {
                return true;
            }
            //Index collection to be created
            List<IndexModel> indexModels = new ArrayList<>();
            for (Map<String, Integer> indexMap : indexList) {
                BasicDBObject index = new BasicDBObject();
                for (String key : indexMap.keySet()) {
                    index.put(key, indexMap.get(key));
                }
                indexModels.add(new IndexModel(index));
            }
            mongoTemplate.createCollection(collName).createIndexes(indexModels);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 更新查询结果的第一条记录
     *
     * @param query          查询条件
     * @param update         更新操作
     * @param collectionName 集合名称
     * @return 是否更新成功
     */
    public boolean updateFirst(Query query, Update update, String collectionName) {
        try {
            mongoTemplate.updateFirst(query, update, collectionName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 更新所有查询结果的记录
     *
     * @param query          查询条件
     * @param update         更新操作
     * @param collectionName 集合名称
     * @return 是否更新成功
     */
    public boolean updateMulti(Query query, Update update, String collectionName) {
        try {
            mongoTemplate.updateMulti(query, update, collectionName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 如果更新对象不存在，则进行插入操作
     *
     * @param query          查询条件
     * @param update         更新操作
     * @param tClass         结果对象的类型
     * @param <T>            结果对象的类型
     * @param collectionName 集合名称
     * @return 是否更新成功
     */
    public <T> boolean upsert(Query query, Update update, Class<T> tClass, String collectionName) {
        try {
            mongoTemplate.upsert(query, update, tClass, collectionName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 存在则更新，不存在则创建
     *
     * @param query          查询条件
     * @param update         更新操作
     * @param collectionName 集合名称
     * @return 是否更新成功
     */
    public boolean upsert(Query query, Update update, String collectionName) {
        try {
            mongoTemplate.upsert(query, update, collectionName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 聚合查询
     *
     * @param aggregation    聚合条件
     * @param tClass         结果对象的类型
     * @param collectionName 集合名称
     * @param <T>            结果对象的类型
     * @return 查询结果集合
     */
    public <T> List<T> groupQuery(Aggregation aggregation, Class<T> tClass, String collectionName) {
        AggregationResults<T> maps = mongoTemplate.aggregate(aggregation, collectionName, tClass);
        return maps.getMappedResults();
    }

    /**
     * 查询集合中符合条件的记录数量
     *
     * @param query          查询条件
     * @param collectionName 集合名称
     * @return 符合条件的记录数量
     */
    public long queryCount(Query query, String collectionName) {
        return mongoTemplate.count(query, collectionName);
    }
}
