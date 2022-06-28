package com.linyonghao.oss.manager.entity;

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
@TableName("core_wo_record")
public class CoreWoRecord implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        private Long id;

      /**
     * 工单id
     */
      private Long woId;

      /**
     * 信息
     */
      private String message;

      /**
     * 空：用户发 非空：管理员id
     */
      private Long adminId;

      /**
     * 发布时间
     */
      private Date publishTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWoId() {
        return woId;
    }

    public void setWoId(Long woId) {
        this.woId = woId;
    }

    public String getMessage() {
        return message;
    }

      public void setMessage(String message) {
          this.message = message;
      }
    
    public Long getAdminId() {
        return adminId;
    }

      public void setAdminId(Long adminId) {
          this.adminId = adminId;
      }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "CoreWoRecord{" +
              ", id=" + id +
                  ", voId=" + woId +
                  ", message=" + message +
                  ", adminId=" + adminId +
                  ", publishTime=" + publishTime +
              "}";
    }
}
