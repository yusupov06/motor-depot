package uz.motordepot.dao.impls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.motordepot.dao.config.ConnectionPool;
import uz.motordepot.dao.contract.RoleDao;
import uz.motordepot.dao.contract.UserDao;
import uz.motordepot.entity.Role;
import uz.motordepot.entity.User;
import uz.motordepot.entity.enums.UserStatus;
import uz.motordepot.exception.DaoException;
import uz.motordepot.instanceHolder.InstanceHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final RoleDao roleDao = InstanceHolder.getInstance(RoleDao.class);
    private final Logger logger = LogManager.getLogger();

    @Override
    public boolean save(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement;
            if (user.getId() != null) {
                statement = connection.prepareStatement(UPDATE_QUERY);
                statement.setLong(7, user.getId());
            } else {
                statement = connection.prepareStatement(SAVE_QUERY);
            }
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getStatus().name());
            statement.setLong(6, user.getRole().getId());

            statement.execute();

            return true;
        } catch (SQLException e) {
            logger.error("Error while saving user");
            throw new DaoException(e.getMessage());
        }

    }

    private User getUserByResult(ResultSet set) throws SQLException {
        User user = new User();
        user.setId(set.getLong(ID));
        user.setFirstName(set.getString(FIRST_NAME));
        user.setLastName(set.getString(LAST_NAME));
        user.setPhoneNumber(set.getString(PHONE_NUMBER));
        user.setPassword(set.getString(PASSWORD));
        user.setStatus(UserStatus.defineValue(set.getString(STATUS)));
        long roleId = set.getLong(ROLE_ID);
        Role role = roleDao.findById(roleId).orElseThrow();
        user.setRole(role);
        return user;
    }

    @Override
    public void delete(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, id);
            statement.executeQuery();
        } catch (SQLException e) {
            logger.error("error while deleting");
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByPhoneNumberAndPassword(String phoneNumber, String password) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_PHONE_NUMBER_AND_PASSWORD);
            statement.setString(1, phoneNumber);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            User user = null;
            if (set.next())
                user = getUserByResult(set);
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            logger.error("error while finding by phone number and password");
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_PHONE_NUMBER);
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next())
                user = getUserByResult(resultSet);
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            logger.error("error while finding by phone number");
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findNameById(long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_WITOUT_ROLE_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong(ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setPhoneNumber(resultSet.getString(PHONE_NUMBER));
                user.setStatus(UserStatus.define(resultSet.getString(STATUS)));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_NAME_BY_PHONE_NUMBER);
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                return true;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return false;
    }


    @Override
    public Optional<User> findById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next())
                user = getUserByResult(resultSet);
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(getUserByResult(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<User> findByPage(int page, int size) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_PAGE);
            statement.setLong(1, (long) (page - 1) * size);
            statement.setLong(2, size);
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(getUserByResult(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

}
