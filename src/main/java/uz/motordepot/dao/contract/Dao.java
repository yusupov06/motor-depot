package uz.motordepot.dao.contract;

import uz.motordepot.dao.config.ConnectionPool;
import uz.motordepot.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Base DAO
 *
 * @param <E>  Entity
 * @param <ID> Id
 */
public interface Dao<E, ID> {

    /**
     * Saving entity method
     *
     * @param entity that is saving
     * @return true if saved false otherwise
     * @throws DaoException when exception occurs when saving
     */
    boolean save(E entity) throws DaoException;

    /**
     * Delete by id
     *
     * @param id that is deleting
     * @throws DaoException when exception occurs
     */
    void delete(ID id) throws DaoException;

    /**
     * Find the entity by id
     *
     * @param id finding entity's id
     * @return Found Empty inside Optional
     * @throws DaoException
     */
    Optional<E> findById(ID id) throws DaoException;

    /**
     * Finding all entities
     * @return List of all entities
     * @throws DaoException
     */

    List<E> findAll() throws DaoException;



    /**
     * Finding entities by pagination
     * @param page page which you called
     * @param size count of entities for per page
     * @return List of entities count is size
     * @throws DaoException
     */
    List<E> findByPage(int page, int size) throws DaoException;



    /**
     * default method. For finding total pages we can make </h1> Example: </h1> 100 entities => 10 pages
     * @param count count of entities for per page
     * @param query database query that finds total counts of entities
     * @return count of pages
     * @throws DaoException
     */
    default int findTotalPages(int count, String query) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long res = resultSet.getLong(1);
                return res > 0 && res < count ? 1 : ((int) (res / count) + (res % count == 0 ? 0 : 1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * default method. For finding total pages we can make <h3>Example: </h3> 100 entities => 10 pages
     * @param size count of entities for per page
     * @param query database query for finding total pages by status
     * @param status status of entity we want to find
     * @return count of pages of entities with this status
     * @throws DaoException
     */
    default int findTotalPagesByStatus(int size, String query, String status) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, status);
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


    /**
     * method that is used for knowing that entity exists with this id or not
     * @param id entity's id
     * @param query database query that finds existed or not
     * @return true if exists otherwise false
     */
    default boolean existsById(ID id, String query) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, (Long) id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
