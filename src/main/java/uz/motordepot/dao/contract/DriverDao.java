package uz.motordepot.dao.contract;

import uz.motordepot.entity.Driver;

import java.util.List;

public interface DriverDao extends Dao<Driver, Long> {

    String ID = "id";
    String CAR_ID = "car_id";
    String USER_ID = "user_id";
    String STATUS = "status";
    String ADDED_BY = "added_by";
    String ADDED_AT = "added_at";

    String UPDATE_QUERY = "update driver set car_id = ?, user_id = ? where id = ?;";
    String INSERT_QUERY = "INSERT INTO driver(car_id, user_id, status,  added_by) VALUES (?,?,?,?);";
    String DELETE_BY_ID = "delete FROM driver WHERE id = ?;";
    String FIND_BY_ID = """
            SELECT * FROM driver where id = ?;
            """;
    String FIND_ALL_QUERY = """
            SELECT * FROM driver order by id desc
            """;
    String FIND_BY_PAGE = "SELECT * FROM driver order by id desc offset ? limit ?;";
    String FIND_BY_PAGE_AND_STATUS = "SELECT * FROM driver where status = ? order by id desc offset ? limit ?;";
    String TOTAL_COUNT_QUERY = "SELECT count(id) FROM driver";
    String TOTAL_COUNT_QUERY_WHERE_STATUS = "SELECT count(id) FROM driver where status = ?";
    String EXIST_BY_ID = "SELECT id FROM driver where id = ?";
    String FIND_STATUS_BY_ID = "SELECT status FROM driver where id = ?";

    /**
     * Finding {@link Driver}s which is in this status by pagination
     * @param page which page
     * @param size count of {@link Driver}s for per page
     * @param status {@link Driver} status
     * @return List of {@link Driver}s
     */
    List<Driver> findByPageAndStatus(int page, int size, String status);

    /**
     * Finding Driver status which is in this id
     * @param id {@link Driver} id
     * @return {@link uz.motordepot.entity.enums.DriverStatus}
     */
    String findStatusById(Long id);
}
