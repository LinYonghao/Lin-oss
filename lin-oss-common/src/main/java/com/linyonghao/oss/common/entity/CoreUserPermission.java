package com.linyonghao.oss.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-16
 */
@TableName("core_user_permission")
public class CoreUserPermission implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户id
     */
      private Integer userId;

      /**
     * 权限id
     */
      private Integer permissionId;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public Integer getUserId() {
        return userId;
    }

      public void setUserId(Integer userId) {
          this.userId = userId;
      }
    
    public Integer getPermissionId() {
        return permissionId;
    }

      public void setPermissionId(Integer permissionId) {
          this.permissionId = permissionId;
      }

    @Override
    public String toString() {
        return "CoreUserPermission{" +
              ", id=" + id +
                  ", userId=" + userId +
                  ", permissionId=" + permissionId +
              "}";
    }
}
