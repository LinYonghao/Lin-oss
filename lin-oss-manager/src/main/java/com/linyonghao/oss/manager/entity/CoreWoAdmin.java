package com.linyonghao.oss.manager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lin Yonghao
 * @since 2022-06-27
 */
@TableName("core_wo_admin")
public class CoreWoAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 工单id
     */
      private Integer woId;

      /**
     * 管理员id
     */
      private Long userId;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public Integer getWoId() {
        return woId;
    }

      public void setWoId(Integer woId) {
          this.woId = woId;
      }
    
    public Long getUserId() {
        return userId;
    }

      public void setUserId(Long userId) {
          this.userId = userId;
      }

    @Override
    public String toString() {
        return "CoreWoAdmin{" +
              ", id=" + id +
                  ", voId=" + woId +
                  ", userId=" + userId +
              "}";
    }
}
