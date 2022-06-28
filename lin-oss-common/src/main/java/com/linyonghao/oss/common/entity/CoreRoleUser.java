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
 * @since 2022-06-27
 */
@TableName("core_role_user")
public class CoreRoleUser implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户id
     */
      private Long userId;

      /**
     * 角色id
     */
      private Integer roleId;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public Long getUserId() {
        return userId;
    }

      public void setUserId(Long userId) {
          this.userId = userId;
      }
    
    public Integer getRoleId() {
        return roleId;
    }

      public void setRoleId(Integer roleId) {
          this.roleId = roleId;
      }

    @Override
    public String toString() {
        return "CoreRoleUser{" +
              ", id=" + id +
                  ", userId=" + userId +
                  ", roleId=" + roleId +
              "}";
    }
}
