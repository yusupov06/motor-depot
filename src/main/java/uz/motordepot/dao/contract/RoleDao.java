package uz.motordepot.dao.contract;

import uz.motordepot.entity.Role;

public interface RoleDao extends Dao<Role, Long> {

    String FIND_ALL_ROLES = "SELECT * FROM ROLE order by id desc;";
    String FIND_BY_PAGE = "SELECT * FROM ROLE order by id desc OFFSET ? LIMIT ?;";
    String COUNT_TOTAL = "SELECT COUNT(id) FROM ROLE;";
    String FIND_ROLE_BY_ID = "SELECT * FROM ROLE WHERE id = ?;";
    String EXIST_BY_ID = "SELECT id FROM ROLE WHERE id = ?;";
    String FIND_ALL_PERMISSIONS = "SELECT * FROM PERMISSION;";
    String DELETE_BY_ID = " DELETE FROM role WHERE id = ?;";
    String FIND_PERMISSIONS_BY_ROLE_ID = "SELECT * FROM PERMISSION WHERE role_id = ?;";

}
