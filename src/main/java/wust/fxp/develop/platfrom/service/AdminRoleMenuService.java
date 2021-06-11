package wust.fxp.develop.platfrom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import wust.fxp.develop.platfrom.dao.AdminRoleMenuDAO;
import wust.fxp.develop.platfrom.entity.AdminRoleMenu;
import wust.fxp.develop.platfrom.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Evan
 * @date 2019/11
 */
@Service
public class AdminRoleMenuService {
    @Autowired
    AdminRoleMenuDAO adminRoleMenuDAO;

    public List<AdminRoleMenu> findAllByRid(int rid) {
        return adminRoleMenuDAO.findAllByRid(rid);
    }

    public List<AdminRoleMenu> findAllByRid(List<Integer> rids) {
        List<AdminRoleMenu> roleMenus=new ArrayList<>();
        if (!CollectionUtils.isEmpty(rids)) {
            for( Integer rid :rids) {
                roleMenus.addAll(adminRoleMenuDAO.findAllByRid(rid));
            }
        }
        return roleMenus;
    }

    public void save(AdminRoleMenu rm) {
        adminRoleMenuDAO.save(rm);
    }

    public void updateRoleMenu(int rid, Map<String, List<Integer>> menusIds) {
        adminRoleMenuDAO.deleteAllByRid(rid);
        List<AdminRoleMenu> rms = new ArrayList<>();
        for (Integer mid : menusIds.get("menusIds")) {
            AdminRoleMenu rm = new AdminRoleMenu();
            rm.setMid(mid);
            rm.setRid(rid);
            adminRoleMenuDAO.save(rm);
        }
    }
}
