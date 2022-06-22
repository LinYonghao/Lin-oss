package com.linyonghao.oss.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-22
 */
@TableName("core_domain")
public class CoreDomain implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        private Long id;

      /**
     * 空间id
     */
        private Long bucketId;

      /**
     * 域名
     */
      private String domain;

      /**
     * 状态 0待解析 1解析成功 2解析异常
     */
      private Integer status;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;
    @TableField(exist = false)
      private String CNAME;

    public String getCNAME() {
        return CNAME;
    }

    public void setCNAME(String CNAME) {
        this.CNAME = CNAME;
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
    
    public String getDomain() {
        return domain;
    }

      public void setDomain(String domain) {
          this.domain = domain;
      }
    
    public Integer getStatus() {
        return status;
    }

      public void setStatus(Integer status) {
          this.status = status;
      }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }

      public void setCreateTime(LocalDateTime createTime) {
          this.createTime = createTime;
      }

    @Override
    public String toString() {
        return "CoreDomain{" +
              ", id=" + id +
                  ", bucketId=" + bucketId +
                  ", domain=" + domain +
                  ", status=" + status +
                  ", createTime=" + createTime +
              "}";
    }
}
