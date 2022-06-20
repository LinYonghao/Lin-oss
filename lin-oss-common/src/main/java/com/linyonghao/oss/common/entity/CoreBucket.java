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
@TableName("core_bucket")
public class CoreBucket implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id 雪花算法
     */
        private Long id;

      /**
     * 用户id
     */
      private Long userId;

      /**
     * 桶名
     */
      private String name;

      /**
     * 访问控制 0公开 1私有
     */
      private Integer ac;

      /**
     * 创建时间
     */
      private Date createTime;


    
    public Long getId() {
        return id;
    }

      public void setId(Long id) {
          this.id = id;
      }
    
    public Long getUserId() {
        return userId;
    }

      public void setUserId(Long userId) {
          this.userId = userId;
      }
    
    public String getName() {
        return name;
    }

      public void setName(String name) {
          this.name = name;
      }
    
    public Integer getAc() {
        return ac;
    }

      public void setAc(Integer ac) {
          this.ac = ac;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public void setCreateTime(Date createTime) {
          this.createTime = createTime;
      }

    @Override
    public String toString() {
        return "CoreBucket{" +
              ", id=" + id +
                  ", userId=" + userId +
                  ", name=" + name +
                  ", ac=" + ac +
                  ", createTime=" + createTime +
              "}";
    }
}
