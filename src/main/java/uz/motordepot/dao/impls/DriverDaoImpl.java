package uz.motordepot.dao.impls;

import uz.motordepot.dao.config.ConnectionPool;
import uz.motordepot.dao.contract.CarDao;
import uz.motordepot.dao.contract.DriverDao;
import uz.motordepot.dao.contract.UserDao;
import uz.motordepot.entity.Car;
import uz.motordepot.entity.Driver;
import uz.motordepot.entity.User;
import uz.motordepot.entity.enums.DriverStatus;
import uz.motordepot.exception.DaoException;
import uz.motordepot.exception.ServiceException;
import uz.motordepot.instanceHolder.InstanceHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverDaoImpl implements DriverDao {

    private final CarDao carDao = InstanceHolder.getInstance(CarDao.class);
    private final UserDao userDao = InstanceHolder.getInstance(UserDao.class);

    @Override
    public boolean save(Driver driver) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement;

            User user;
            if (userDao.save(driver.getUser())) {
                user = userDao.findByPhoneNumber(driver.getUser().getPhoneNumber()).orElseThrow(() -> ServiceException.throwExc("USER NOT SAVED", 405));
            } else
                throw ServiceException.throwExc("Error while saving driver", 404);

            if (driver.getId() != null) {
                statement = connection.prepareStatement(UPDATE_QUERY);
                statement.setLong(3, driver.getId());
            } else {
                statement = connection.prepareStatement(INSERT_QUERY);
                statement.setString(3, driver.getStatus().name());
                statement.setLong(4, driver.getAddedBy().getId());
            }
            boolean b = carDao.setAsActive(driver.getCar().getId());
            System.out.println(b);
            statement.setLong(1, driver.getCar().getId());
            statement.setLong(2, user.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private Driver getDriverByResult(ResultSet resultSet) throws SQLException {

        Driver driver = new Driver();
        driver.setId(resultSet.getLong(ID));
        long carId = resultSet.getLong(CAR_ID);

        Car car = carDao.findById(carId).orElseThrow(() -> ServiceException.throwExc("CAR NOT FOUND WITH ID " + carId, 404));
        driver.setCar(car);

        User user = userDao
                .findWithoutPermissionsById(resultSet.getLong(USER_ID))
                .orElseThrow(() -> ServiceException.throwExc("Driver (user) not found", 404));

        User addedBy = userDao
                .findNameById(resultSet.getLong(ADDED_BY))
                .orElseThrow(() -> ServiceException.throwExc("Adder not found", 404));

        String status = resultSet.getString(STATUS);
        driver.setStatus(DriverStatus.define(status));
        driver.setUser(user);
        driver.setAddedBy(addedBy);
        Timestamp timestamp = resultSet.getTimestamp(ADDED_AT);
        driver.setAddedAt(timestamp.toLocalDateTime());
        return driver;
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
    public Optional<Driver> findById(Long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Driver driver = new Driver();
            if (resultSet.next()) driver = getDriverByResult(resultSet);
            return Optional.of(driver);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Driver> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            Driver driverByResult;
            while (resultSet.next()) {
                driverByResult = getDriverByResult(resultSet);
                drivers.add(driverByResult);
            }
            return drivers;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Driver> findByPage(int page, int size) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_PAGE);
            statement.setLong(1, (long) (page - 1) * size);
            statement.setLong(2, size);
            ResultSet resultSet = statement.executeQuery();
            List<Driver> drivers = new ArrayList<>(size);
            Driver driverByResult;
            while (resultSet.next()) {
                driverByResult = getDriverByResult(resultSet);
                drivers.add(driverByResult);
            }
            return drivers;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Driver> findByPageAndStatus(int page, int size, String status) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_PAGE_AND_STATUS);
            statement.setString(1, status);
            statement.setLong(2, (long) (page - 1) * size);
            statement.setLong(3, size);
            ResultSet resultSet = statement.executeQuery();
            List<Driver> drivers = new ArrayList<>(size);
            Driver driverByResult;
            while (resultSet.next()) {
                driverByResult = getDriverByResult(resultSet);
                drivers.add(driverByResult);
            }
            return drivers;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public String findStatusById(Long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_STATUS_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else
                throw ServiceException.throwExc("Driver not found with id " + id, 404);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
