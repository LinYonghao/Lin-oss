package com.linyonghao.oss.manager.config;


import java.util.ArrayList;
import java.util.List;

import com.linyonghao.oss.common.entity.CorePermission;
import com.linyonghao.oss.common.entity.CoreRole;
import com.linyonghao.oss.common.service.ICoreRoleUserService;
import com.linyonghao.oss.manager.service.PermissionService;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.dev33.satoken.stp.StpInterface;

/**
 * 自定义权限验证接口扩展
 */
@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ICoreRoleUserService coreRoleUserService;
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // TODO 通过角色获取对于的权限
        List<CorePermission> corePermissions = permissionService.get(String.valueOf(loginId));
        List<String> permissions = new ArrayList<>();
        corePermissions.forEach((v)->{
            permissions.add(v.getPermissionKey());
        });
        return permissions;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<CoreRole> roleByUserId = coreRoleUserService.getRoleByUserId(String.valueOf(loginId));
        ArrayList<String> coreRoles = new ArrayList<>();
        roleByUserId.forEach((v)->{
            coreRoles.add(v.getName());
        });
        return coreRoles;
    }

}
