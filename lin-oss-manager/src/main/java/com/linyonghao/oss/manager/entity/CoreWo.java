package com.linyonghao.oss.manager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lin Yonghao
 * @since 2022-06-27
 */
@TableName("core_wo")
public class CoreWo implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 用户id
     */
      private Long userId;

      /**
     * 标题
     */
      private String title;

      /**
     * 空间
     */
      private String bucket;

      /**
     * 描述
     */
      private String description;

      /**
     * 手机号码
     */
      private String mobile;

      /**
     * 邮箱
     */
      private String email;

      /**
     * 创建时间
     */
      private Date createTime;

      private int status;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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
    
    public String getTitle() {
        return title;
    }

      public void setTitle(String title) {
          this.title = title;
      }
    
    public String getBucket() {
        return bucket;
    }

      public void setBucket(String bucket) {
          this.bucket = bucket;
      }
    
    public String getDescription() {
        return description;
    }

      public void setDescription(String description) {
          this.description = description;
      }
    
    public String getMobile() {
        return mobile;
    }

      public void setMobile(String mobile) {
          this.mobile = mobile;
      }
    
    public String getEmail() {
        return email;
    }

      public void setEmail(String email) {
          this.email = email;
      }
    

    @Override
    public String toString() {
        return "CoreWo{" +
              ", id=" + id +
                  ", userId=" + userId +
                  ", title=" + title +
                  ", bucket=" + bucket +
                  ", description=" + description +
                  ", mobile=" + mobile +
                  ", email=" + email +
                  ", createTime=" + createTime +
              "}";
    }
}
