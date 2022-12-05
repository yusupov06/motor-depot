package uz.motordepot.dao.contract;

import uz.motordepot.entity.User;
import uz.motordepot.exception.DaoException;

import java.util.Optional;

public interface UserDao extends Dao<User, Long> {
    String ID = "id";
    String FIRST_NAME = "firstname";
    String LAST_NAME = "lastname";
    String PHONE_NUMBER = "phone_number";
    String PASSWORD = "password";
    String STATUS = "status";
    String ROLE_ID = "role_id";
    String DELETE_BY_ID = "DELETE FROM users WHERE id = ?;";
    String SAVE_QUERY =
            "INSERT INTO USERS(firstname, lastname, phone_number, password, status, role_id) VALUES (?,?,?,?,?,?);";

    String UPDATE_QUERY = """
                update users set firstname = ?, lastname = ?, phone_number = ?, password = ?, status = ?,  role_id = ? where id = ?;
            """;
    String UPDATE_QUERY_WITHOUT_ROLE = """
                update users set firstname = ?, lastname = ?, phone_number = ?, password = ?, status = ? where id = ?;
            """;
    String FIND_BY_PHONE_NUMBER_AND_PASSWORD = "SELECT * FROM USERS WHERE phone_number LIKE ? AND password LIKE ?;";
    String FIND_BY_ID = "SELECT * FROM USERS WHERE id = ?;";
    String EXISTS_BY_ID = "SELECT * FROM USERS WHERE id = ?;";
    String FIND_WITHOUT_ROLE_BY_ID = "SELECT id, firstname, lastname, phone_number, status FROM USERS WHERE id = ?;";
    String EXISTS_BY_PHONE_NUMBER = "SELECT id FROM USERS WHERE phone_number = ?;";
    String FIND_BY_PHONE_NUMBER = "SELECT * FROM USERS WHERE phone_number = ?;";
    String FIND_ALL = "SELECT * FROM USERS order by id desc;";
    String FIND_BY_PAGE = "SELECT * FROM USERS order by id desc offset ? limit ?;";
    String TOTAL_COUNT_QUERY = "SELECT count(id) FROM USERS;";

    /**
     * Finding User by phoneNu,ber and password
     * @param phoneNumber phoneNumber of {@link User}
     * @param password password of {@link User}
     * @return User with this phoneNumber and password
     * @throws DaoException
     */
    Optional<User> findByPhoneNumberAndPassword(String phoneNumber, String password) throws DaoException;


    /**
     * Finding {@link User} by phoneNumber
     * @param phoneNumber
     * @return user with this phoneNumber
     * @throws DaoException
     */
    Optional<User> findByPhoneNumber(String phoneNumber) throws DaoException;

    /**
     * Finding {@link User} with only id, firstname, lastname and phoneNumber
     * @param id {@link User} id we are finding
     * @return User with this id
     * @throws DaoException
     */
    Optional<User> findNameById(long id) throws DaoException;

    /**
     * Check For existing by phoneNumber
     * @param phoneNumber
     */
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findWithoutPermissionsById(long id);
}
