package wust.fxp.develop.platfrom.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import wust.fxp.develop.platfrom.dao.AdminRoleDAO;
import wust.fxp.develop.platfrom.entity.AdminMenu;
import wust.fxp.develop.platfrom.entity.AdminPermission;
import wust.fxp.develop.platfrom.entity.AdminRole;
import wust.fxp.develop.platfrom.entity.AdminUserRole;
import wust.fxp.develop.platfrom.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evan
 * @date 2019/11
 */
@Service
public class AdminRoleService {
    @Autowired
    AdminRoleDAO adminRoleDAO;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminPermissionService adminPermissionService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    @Autowired
    AdminMenuService adminMenuService;

    public List<AdminRole> listWithPermsAndMenus() {
        List<AdminRole> roles = adminRoleDAO.findAll();
        List<AdminPermission> perms;
        List<AdminMenu> menus;
        for (AdminRole role : roles) {
            perms = adminPermissionService.listPermsByRoleId(role.getId());
            menus = adminMenuService.getMenusByRoleId(role.getId());
            role.setPerms(perms);
            role.setMenus(menus);
        }
        return roles;
    }

    public List<AdminRole> findAll() {
        return adminRoleDAO.findAll();
    }


    public void addOrUpdate(AdminRole adminRole) {
        adminRoleDAO.save(adminRole);
    }

    public List<AdminRole> listRolesByUser(String username) {
        int uid = userService.findByUsername(username).getId();
        List<Integer> rids = adminUserRoleService.listAllByUid(uid)
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());
        List<AdminRole> roleList=new ArrayList<>();
        for(Integer rid:rids){
            roleList.add(adminRoleDAO.findById(rid));
        }
        return roleList;
    }

    public AdminRole updateRoleStatus(AdminRole role) {
        AdminRole roleInDB = adminRoleDAO.findById(role.getId());
        roleInDB.setEnabled(role.isEnabled());
        adminRoleDAO.updateStates(roleInDB);
        return roleInDB;
    }

    public void editRole(@RequestBody AdminRole role) {
        adminRoleDAO.save(role);
        if(!CollectionUtils.isEmpty(role.getPerms())) {
            adminRolePermissionService.savePermChanges(role.getId(), role.getPerms());
        }
    }
}
