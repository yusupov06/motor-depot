package uz.motordepot.dao.impls;

import org.jetbrains.annotations.NotNull;
import uz.motordepot.dao.config.ConnectionPool;
import uz.motordepot.dao.contract.RequestDao;
import uz.motordepot.dao.contract.UserDao;
import uz.motordepot.entity.Request;
import uz.motordepot.entity.User;
import uz.motordepot.entity.enums.RequestStatus;
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


public class RequestDaoImpl implements RequestDao {

    private final UserDao userDao = InstanceHolder.getInstance(UserDao.class);

    @Override
    public boolean save(@NotNull Request request) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement;
            if (request.getId() != null) {
                statement = connection.prepareStatement(UPDATE_QUERY);
                statement.setLong(5, request.getId());
            } else {
                statement = connection.prepareStatement(INSERT_QUERY);
                statement.setLong(5, request.getAddedBy().getId());
            }
            statement.setString(1, request.getName());
            statement.setString(2, request.getFrom());
            statement.setString(3, request.getTo());
            statement.setString(4, request.getStatus().name());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private @NotNull Request getFromResult(@NotNull ResultSet resultSet) throws SQLException {
        Request request = new Request();
        request.setId(resultSet.getLong(ID));
        request.setName(resultSet.getString(NAME));
        request.setFrom(resultSet.getString(FROM));
        request.setTo(resultSet.getString(TO));
        LocalDateTime dateTime = resultSet.getTimestamp(ADDED_AT).toLocalDateTime();
        request.setAddedAt(dateTime);
        long addedBy = resultSet.getLong(ADDED_BY);
        User user = userDao.findNameById(addedBy).orElseThrow(() -> ServiceException.throwExc("User not found with id " + addedBy, 404));
        request.setStatus(RequestStatus.valueOf(resultSet.getString(STATUS)));
        request.setAddedBy(user);
        return request;
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Optional<Request> findById(Long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Request request = null;
            if (resultSet.next())
                request = getFromResult(resultSet);
            return Optional.ofNullable(request);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Request> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            List<Request> requests = new ArrayList<>();
            while (resultSet.next()) {
                requests.add(getFromResult(resultSet));
            }
            return requests;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Request> findByPage(int page, int size) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_PAGE_QUERY);
            statement.setLong(1, (long) (page - 1) * size);
            statement.setLong(2, size);
            ResultSet resultSet = statement.executeQuery();
            List<Request> requests = new ArrayList<>();
            while (resultSet.next()) {
                requests.add(getFromResult(resultSet));
            }
            return requests;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }


    @Override
    public List<Request> findPageByStatus(int page, int size, RequestStatus status) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_PAGE_AND_STATUS_QUERY);
            statement.setString(1, status.name());
            statement.setLong(2, (long) (page - 1) * size);
            statement.setLong(3, size);
            ResultSet resultSet = statement.executeQuery();
            List<Request> requests = new ArrayList<>();
            while (resultSet.next()) {
                requests.add(getFromResult(resultSet));
            }
            return requests;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public String getStatusById(Long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_STATUS_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else
                throw ServiceException.throwExc("Not Found with id " + id, 404);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public int findTotalPagesByAdderId(int size, Long adderId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_TOTAL_COUNT_QUERY_BY_ADDER_ID);
            statement.setLong(1, adderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long res = resultSet.getLong(1);
                return res > 0 && res < size ? 1 : ((int) (res / size) + (res % size == 0 ? 0 : 1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<Request> findPageByAdderId(int page, int pageCount, Long adderId) {

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_PAGE_AND_ADDER_ID);
            statement.setLong(1, adderId);
            statement.setLong(2, (long) (page - 1) * pageCount);
            statement.setLong(3, pageCount);
            ResultSet resultSet = statement.executeQuery();
            List<Request> requests = new ArrayList<>();
            while (resultSet.next()) {
                requests.add(getFromResult(resultSet));
            }
            return requests;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Optional<Request> findByIdAndAdder(Long id, Long addedBy) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_AND_ADDER_QUERY);
            statement.setLong(1, id);
            statement.setLong(2, addedBy);
            ResultSet resultSet = statement.executeQuery();
            Request s = null;
            if (resultSet.next())
                 s = getFromResult(resultSet);
            return Optional.ofNullable(s);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
