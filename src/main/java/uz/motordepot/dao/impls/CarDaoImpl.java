package uz.motordepot.dao.impls;

import org.jetbrains.annotations.NotNull;
import uz.motordepot.dao.config.ConnectionPool;
import uz.motordepot.dao.contract.CarDao;
import uz.motordepot.dao.contract.UserDao;
import uz.motordepot.entity.Car;
import uz.motordepot.entity.User;
import uz.motordepot.entity.enums.CarCondition;
import uz.motordepot.entity.enums.CarModel;
import uz.motordepot.exception.DaoException;
import uz.motordepot.instanceHolder.InstanceHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDaoImpl implements CarDao {

    private final UserDao userDao = InstanceHolder.getInstance(UserDao.class);

    @Override
    public boolean save(@NotNull Car car) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement;
            if (car.getId() != null) {
                statement = connection.prepareStatement(UPDATE_QUERY);
                statement.setLong(4, car.getId());
                statement.setString(3, car.getCondition().name());
            } else {
                statement = connection.prepareStatement(INSERT_QUERY);
                statement.setString(3, CarCondition.NOT_ACTIVE.name());
                statement.setLong(4, car.getAddedBy().getId());
            }

            statement.setString(1, car.getCarModel().name());
            statement.setString(2, car.getCarNumber());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private @NotNull Car getCarByResult(@NotNull ResultSet set) throws SQLException {
        Car car = new Car();
        car.setId(set.getLong(ID));
        car.setCarModel(CarModel.valueOf(set.getString(CAR_MODEL)));
        car.setCarNumber(set.getString(CAR_NUMBER));
        car.setCondition(CarCondition.define(set.getString(CONDITION)));
        LocalDateTime dateTime = set.getTimestamp(ADDED_AT).toLocalDateTime();
        long addedBy = set.getLong(ADDED_BY);
        car.setAddedAt(dateTime);
        User user = userDao
                .findNameById(addedBy)
                .orElseThrow(() -> new DaoException("User not found with id " + addedBy));
        car.setAddedBy(user);
        return car;
    }

    @Override
    public void delete(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Optional<Car> findById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Car car = null;
            if (resultSet.next())
                car = getCarByResult(resultSet);
            return Optional.ofNullable(car);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Car> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(getCarByResult(resultSet));
            }
            return cars;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Car> findByPage(int page, int size) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_PAGE_QUERY);
            statement.setLong(1, (long) size * (page - 1));

            statement.setLong(2, size);
            ResultSet resultSet = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(getCarByResult(resultSet));
            }
            return cars;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Car> findByPageAndCondition(int page, int size, String condition) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_PAGE_AND_CONDITION_QUERY);
            statement.setString(1, condition);
            statement.setLong(2, (long) (page - 1) * size);
            statement.setLong(3, size);
            ResultSet resultSet = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(getCarByResult(resultSet));
            }
            return cars;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public int findTotalPagesByCondition(String condition, int size) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(TOTAL_COUNT_QUERY_AND_CONDITION);
            statement.setString(1, condition);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                long res = set.getLong(1);
                return res > 0 && res <= size ? 1 : (int) (res / size);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean setAsActive(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_TO_CONDITION_BY_ID);
            statement.setString(1, CarCondition.ACTIVE.name());
            statement.setLong(2, id);
            return statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public String findConditionById(Long carId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_CONDITION_BY_ID);
            statement.setLong(1, carId);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return set.getString(1);
            } else
                throw new DaoException("");
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
