package com.linyonghao.oss.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-17
 */
@TableName("core_object")
public class CoreObject implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id 雪花算法
     */
        private Long id;

      /**
     * 桶id
     */
      private Long bucketId;

      /**
     * 键
     */
      private String remoteKey;

      /**
     * 创建时间
     */
      private Date createTime;

      /**
     * Mine类型
     */
      private String mine;

      /**
     * 服务器路径
     */
      private String localKey;

    /**
     * 文件大小
     *
     */
    private long size;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Long getId() {
        return id;
    }

      public void setId(Long id) {
          this.id = id;
      }
    
    public Long getBucketId() {
        return bucketId;
    }

      public void setBucketId(Long bucketId) {
          this.bucketId = bucketId;
      }
    
    public String getRemoteKey() {
        return remoteKey;
    }

      public void setRemoteKey(String remoteKey) {
          this.remoteKey = remoteKey;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public void setCreateTime(Date createTime) {
          this.createTime = createTime;
      }
    
    public String getMine() {
        return mine;
    }

      public void setMine(String mine) {
          this.mine = mine;
      }
    
    public String getLocalKey() {
        return localKey;
    }

      public void setLocalKey(String localKey) {
          this.localKey = localKey;
      }

    @Override
    public String toString() {
        return "CoreObject{" +
              ", id=" + id +
                  ", bucketId=" + bucketId +
                  ", key=" + remoteKey +
                  ", createTime=" + createTime +
                  ", mine=" + mine +
                  ", localKey=" + localKey +
              "}";
    }
}
