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
@TableName("core_permission")
public class CorePermission implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 权限键
     */
      private String permissionKey;

      /**
     * 权限说明
     */
      private String description;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public String getPermissionKey() {
        return permissionKey;
    }

      public void setPermissionKey(String permissionKey) {
          this.permissionKey = permissionKey;
      }
    
    public String getDescription() {
        return description;
    }

      public void setDescription(String description) {
          this.description = description;
      }

    @Override
    public String toString() {
        return "CorePermission{" +
              ", id=" + id +
                  ", permissionKey=" + permissionKey +
                  ", description=" + description +
              "}";
    }
}
