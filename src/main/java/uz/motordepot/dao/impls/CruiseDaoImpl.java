package uz.motordepot.dao.impls;

import org.jetbrains.annotations.NotNull;
import uz.motordepot.dao.config.ConnectionPool;
import uz.motordepot.dao.contract.CruiseDao;
import uz.motordepot.dao.contract.DriverDao;
import uz.motordepot.dao.contract.RequestDao;
import uz.motordepot.dao.contract.UserDao;
import uz.motordepot.entity.Cruise;
import uz.motordepot.entity.Driver;
import uz.motordepot.entity.Request;
import uz.motordepot.entity.User;
import uz.motordepot.entity.enums.CruiseStatus;
import uz.motordepot.entity.enums.DriverStatus;
import uz.motordepot.exception.DaoException;
import uz.motordepot.exception.ServiceException;
import uz.motordepot.instanceHolder.InstanceHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CruiseDaoImpl implements CruiseDao {

    private final DriverDao driverDao = InstanceHolder.getInstance(DriverDao.class);
    private final RequestDao requestDao = InstanceHolder.getInstance(RequestDao.class);
    private final UserDaoImpl userDao = InstanceHolder.getInstance(UserDao.class);

    @Override
    public boolean save(@NotNull Cruise cruise) {

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement;
            if (cruise.getId() != null) {
                statement = connection.prepareStatement(UPDATE_QUERY);
                statement.setLong(4, cruise.getId());
            } else {
                statement = connection.prepareStatement(INSERT_QUERY);
                statement.setLong(4, cruise.getAddedBy().getId());
            }
            statement.setLong(1, cruise.getDriver().getId());
            statement.setLong(2, cruise.getRequest().getId());
            statement.setString(3, cruise.getStatus().name());
            return statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private Cruise getCruiseByResult(@NotNull ResultSet resultSet) throws SQLException {

        Cruise cruise = new Cruise();
        long id = resultSet.getLong(ID);
        cruise.setId(id);

        long driverId = resultSet.getLong(DRIVER);

        Driver driver = driverDao
                .findById(driverId)
                .orElseThrow(() -> ServiceException.throwExc("DRIVER NOT FOUND"));

        cruise.setDriver(driver);

        long requestId = resultSet.getLong(REQUEST);
        Request request = requestDao
                .findById(requestId)
                .orElseThrow(() -> ServiceException.throwExc("REQUEST NOT FOUND WITH ID " + requestId));

        cruise.setRequest(request);
        cruise.setStatus(CruiseStatus.valueOf(resultSet.getString(STATUS)));
        LocalDateTime added_at = resultSet.getTimestamp(ADDED_AT).toLocalDateTime();
        cruise.setAddedAt(added_at);
        cruise.setNote(resultSet.getString(NOTE));
        long addedBy = resultSet.getLong(ADDED_BY);

        User user = userDao.findNameById(addedBy)
                .orElseThrow(() -> ServiceException.throwExc("USER NOT FOUND WITH ID " + addedBy));
        cruise.setAddedBy(user);
        return cruise;
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, id);
            statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Optional<Cruise> findById(Long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Cruise cruise = null;
            if (resultSet.next())
                cruise = getCruiseByResult(resultSet);
            return Optional.ofNullable(cruise);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Cruise> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL);
            System.out.println("statement = " + statement);
            ResultSet resultSet = statement.executeQuery();
            List<Cruise> cruises = new ArrayList<>();
            while (resultSet.next()) {
                cruises.add(getCruiseByResult(resultSet));
            }
            return cruises;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Cruise> findByPage(int page, int size) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_PAGE);
            statement.setLong(1, (long) (page-1) * size);
            statement.setLong(2, size);
            ResultSet resultSet = statement.executeQuery();
            List<Cruise> cruises = new ArrayList<>();
            while (resultSet.next()) {
                cruises.add(getCruiseByResult(resultSet));
            }
            return cruises;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public List<Cruise> findAllByOwnerId(Long managerId) {
        return findAll()
                .stream()
                .filter(cruise -> cruise.getAddedBy().getId().equals(managerId))
                .toList();
    }

    public List<Cruise> findAllByDriverId(Long driverId) {
        return findAll()
                .stream()
                .filter(cruise -> cruise.getDriver().getId().equals(driverId))
                .toList();
    }

    @Override
    public DriverStatus findDriverStatusFromCruises(long driverId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_DRIVER_STATUS_FROM_CRUISES_BY_DRIVER_ID);
            statement.setLong(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return DriverStatus.define(resultSet.getString(1));
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return DriverStatus.FREE;
    }
    
    @Override
    public int findTotalPagesWhereDriverId(int size, Long driverId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(TOTAL_COUNT_QUERY_BY_DRIVER_ID);
            statement.setLong(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return (int) (resultSet.getLong(1) / size);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return 0;
    }

    @Override
    public List<Cruise> findByPageAndDriverUserId(int page, int size, Long driverUserId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_PAGE_AND_DRIVER_USER_ID);
            statement.setLong(1, driverUserId);
            statement.setLong(2, (long) (page-1) * size);
            statement.setLong(3, size);
            ResultSet resultSet = statement.executeQuery();
            List<Cruise> cruises = new ArrayList<>();
            while (resultSet.next()) {
                cruises.add(getCruiseByResult(resultSet));
            }
            return cruises;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

    }

    @Override
    public boolean changeStatusById(String changedStatus, long cruiseId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CHANGE_STATUS_BY_ID);
            statement.setLong(2, cruiseId);
            statement.setString(1, changedStatus);
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public boolean addNote(long cruiseId, String note) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CHANGE_NOTE_BY_ID);
            statement.setLong(2, cruiseId);
            statement.setString(1, note);
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
