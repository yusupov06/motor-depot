package uz.motordepot.dao.impls;

import uz.motordepot.dao.config.ConnectionPool;
import uz.motordepot.dao.contract.RoleDao;
import uz.motordepot.entity.Role;
import uz.motordepot.entity.enums.PermissionEnum;
import uz.motordepot.entity.enums.UserRole;
import uz.motordepot.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RoleDaoImpl implements RoleDao {

    @Override
    public boolean save(Role role) {
        return false;
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Optional<Role> findById(Long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ROLE_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Role role = getRoleByResult(resultSet);
                return Optional.of(role);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return Optional.empty();
    }

    private Set<PermissionEnum> findPermissionsByRoleId(Long roleId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_PERMISSIONS_BY_ROLE_ID);
            statement.setLong(1, roleId);
            ResultSet resultSet = statement.executeQuery();
            return getPermissionsByResult(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private Set<PermissionEnum> getPermissionsByResult(ResultSet resultSet) throws SQLException {
        Set<PermissionEnum> permissions = new HashSet<>();
        while (resultSet.next()) {
            permissions.add(getByResult(resultSet));
        }
        return permissions;
    }

    private PermissionEnum getByResult(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        return PermissionEnum.valueOf(name);
    }

    @Override
    public List<Role> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_ROLES);
            ResultSet resultSet = statement.executeQuery();
            List<Role> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(getRoleByResult(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Role> findByPage(int page, int size) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_PAGE);
            statement.setLong(1, (long) (page - 1) * size);
            statement.setLong(2, size);
            ResultSet resultSet = statement.executeQuery();
            List<Role> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(getRoleByResult(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }


    private Role getRoleByResult(ResultSet resultSet) throws SQLException {

        Role role = new Role();
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        role.setId(id);
        role.setName(UserRole.define(name));
        role.setDescription(description);
        Set<PermissionEnum> permissionsByRoleId = findPermissionsByRoleId(id);
        role.setPermissions(permissionsByRoleId);
        return role;
    }


}
