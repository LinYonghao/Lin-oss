package com.linyonghao.oss.common.service.impl;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyonghao.influxdb2.entity.CountWithTime;
import com.linyonghao.oss.common.dao.mapper.relationship.CoreObjectMapper;
import com.linyonghao.oss.common.dao.mapper.sequential.APILogMapper;
import com.linyonghao.oss.common.entity.CoreObject;
import com.linyonghao.oss.common.entity.DirectoryTree;
import com.linyonghao.oss.common.model.APILogModel;
import com.linyonghao.oss.common.service.ICoreObjectService;
import com.linyonghao.oss.common.utils.DateUtil;
import com.linyonghao.oss.common.utils.DirectoryTreeUtil;
import com.linyonghao.oss.common.utils.StringUtil;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-17
 */
@Service
public class CoreObjectServiceImpl extends ServiceImpl<CoreObjectMapper, CoreObject> implements ICoreObjectService {

    @Autowired
    private APILogMapper apiLogMapper;

    @Autowired
    private DirectoryTreeUtil directoryTreeUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${redis.key.bucket.dir_tree}")
    private String BUCKET_DIR_TREE_KEY;

    @Value("${redis.database}")
    private String DATABASE;




    /**
     * 获取某个bucket的对象数量和对象总存储量
     * @param userId
     * @param bucketId
     * @return int[objectNum,sizeSum]
     */
    @Override
    public long[] getObjectNumAndSizeSumByBucket(String userId, String bucketId) {
        Map<String, Object> resultMap = this.getBaseMapper().selectObjectNumAndSizeSumByBucket(userId, bucketId);
        if(resultMap.size() != 2){
            return new long[]{0L,0L};
        }
        String size = resultMap.get("size").toString();
        String count = resultMap.get("count").toString();
        return new long[]{Long.parseLong(count), Long.parseLong(size)};
    }



    @Override
    public DirectoryTree getDirTreeByDir(String dir, String bucketId) {
        if(dir == null){
            dir = "/";
        }
        Map<String, DirectoryTree> treeMap = getDirectoryTree(bucketId);
        return treeMap.get(dir);

    }

    @Override
    public Map<String, DirectoryTree> getDirectoryTree(String bucketId) {
        List<CoreObject> objectList;
        String s = redisTemplate.opsForValue().get(StringUtil.generateRedisKey(DATABASE, BUCKET_DIR_TREE_KEY, bucketId));
        Map<String, DirectoryTree> treeMap;
        if(s == null){
            QueryWrapper<CoreObject> wrapper = new QueryWrapper<>();
            wrapper.eq("bucket_id",bucketId);
            objectList = this.getBaseMapper().selectList(wrapper);

            treeMap = directoryTreeUtil.resolve(objectList);
            redisTemplate.opsForValue().set(StringUtil.generateRedisKey(DATABASE,BUCKET_DIR_TREE_KEY,bucketId),
                    JSON.toJSONString(treeMap));
            return treeMap;

        }else{
            TypeReference<Map<String, DirectoryTree>> typeReference = new TypeReference<Map<String, DirectoryTree>>() {};
            treeMap = JSON.parseObject(s,typeReference.getType());
        }
        return treeMap;
    }

    @Override
    public Map<String, DirectoryTree> addFile(CoreObject coreObject) {
        this.save(coreObject);
        // 添加文件采用动态添加 删除采用重建节点
        Map<String, DirectoryTree> directoryTree = getDirectoryTree(coreObject.getBucketId().toString());
        Map<String, DirectoryTree> treeMap = directoryTreeUtil.addFile(coreObject, directoryTree);
        redisTemplate.opsForValue().set(StringUtil.generateRedisKey(DATABASE,BUCKET_DIR_TREE_KEY,
                        coreObject.getBucketId().toString()),
                JSON.toJSONString(treeMap));
        return treeMap;
    }

    @Override
    public boolean removeOneObject(String bucketId, String key) {
        QueryWrapper<CoreObject> wrapper = new QueryWrapper<>();
        wrapper.eq("bucket_id",bucketId);
        wrapper.eq("remote_key",key);
        int row = getBaseMapper().delete(wrapper);
        if(row > 0){
            redisTemplate.delete(StringUtil.generateRedisKey(DATABASE,BUCKET_DIR_TREE_KEY,bucketId));
        }
        return row > 0;
    }

    @Override
    public CoreObject getByKey(String bucketId,String remoteKey) {
        QueryWrapper<CoreObject> wrapper = new QueryWrapper<>();
        wrapper.eq("bucket_id",bucketId);
        wrapper.eq("remote_key",remoteKey);
        return getBaseMapper().selectOne(wrapper);
    }

    /**
     * 更新key
     * @param bucketId 空间id
     * @param key 需修改的键
     * @param newKey 新键
     * @return 是否修改成功
     */
    @Override
    @Transactional
    public boolean updateKey(String bucketId, String key, String newKey) {
        // 更新库
        QueryWrapper<CoreObject> wrapper = new QueryWrapper<>();
        wrapper.eq("bucket_id",bucketId);
        wrapper.eq("remote_key",key);
        CoreObject object = getBaseMapper().selectOne(wrapper);
        object.setRemoteKey(newKey);
        getBaseMapper().updateById(object);
        // 删除redis 下次会重建节点数
        return Boolean.TRUE.equals(redisTemplate.delete(StringUtil.generateRedisKey(DATABASE, BUCKET_DIR_TREE_KEY, bucketId)));
    }
}
