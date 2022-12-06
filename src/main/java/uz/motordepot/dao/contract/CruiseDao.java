package uz.motordepot.dao.contract;

import uz.motordepot.entity.Cruise;
import uz.motordepot.entity.enums.DriverStatus;

import java.util.List;

public interface CruiseDao extends Dao<Cruise, Long> {

    String ID = "id";
    String DRIVER = "driver_id";
    String REQUEST = "request_id";
    String STATUS = "status";
    String NOTE = "note";
    String ADDED_AT = "added_at";
    String ADDED_BY = "added_by";
    String UPDATE_QUERY = "update cruise set driver_id = ?, request_id = ?, status = ? where id = ?;";
    String INSERT_QUERY = "INSERT INTO cruise(driver_id, request_id, status, added_by) VALUES (?,?,?,?);";
    String DELETE_BY_ID = "delete FROM cruise WHERE id = ?;";
    String FIND_BY_ID = "select * from cruise where id = ?;";
    String FIND_BY_PAGE_AND_DRIVER_USER_ID = """
            SELECT *
            FROM cruise
                     join driver d on d.id = cruise.driver_id
                     join users u on u.id = d.user_id
            where u.id = ? order by cruise.id desc
            offset ? limit ?;
            """;
    String FIND_ALL = "SELECT * FROM cruise order by id desc;";
    String TOTAL_COUNT_QUERY = "SELECT COUNT(id) FROM cruise;";
    String TOTAL_COUNT_QUERY_BY_DRIVER_ID = "SELECT COUNT(id) FROM cruise where driver_id = ?;";
    String FIND_DRIVER_STATUS_FROM_CRUISES_BY_DRIVER_ID = "select d.status from cruise join driver d on d.id = cruise.driver_id where d.id = ?;";
    String FIND_BY_PAGE = "SELECT * FROM cruise order by id desc offset ? limit ?;";
    String EXIST_BY_ID = "SELECT id FROM cruise where id = ?;";
    String CHANGE_STATUS_BY_ID = "update cruise set status = ? where id = ?;";
    String CHANGE_NOTE_BY_ID = "update cruise set note = ? where id = ?;";

    /**
     * Finding all {@link Cruise}s of which manager is this
     * @param managerId id of adder of this Cruise
     * @return List of Cruises which belongs to managerId
     */
    List<Cruise> findAllByOwnerId(Long managerId);


    /**
     * Find all {@link Cruise}s of which driver id is
     * @param driverId id of Driver
     * @return List of {@link Cruise}s
     */
    List<Cruise> findAllByDriverId(Long driverId);

    /**
     * Find {@link DriverStatus} of {@link Cruise} of which driverId is this id
     * @param driverId {@link Cruise}'s driverId
     * @return DriverStatus
     */
    DriverStatus findDriverStatusFromCruises(long driverId);

    /**
     * Finding total pages where driverId of {@link Cruise} is this id
     * @param size count of {@link Cruise}s for per page
     * @param driverId {@link Cruise}'s driverId
     * @return count of total pages
     */
    int findTotalPagesWhereDriverId(int size, Long driverId);

    /**
     * Finding {@link Cruise}s which {@link uz.motordepot.entity.Driver}'s {@link uz.motordepot.entity.User} id is this
     * @param page page for pagination
     * @param size count of {@link Cruise}s for per page
     * @param driverUserId {@link uz.motordepot.entity.Driver}'s userId
     * @return List of {@link Cruise}s
     */
    List<Cruise> findByPageAndDriverUserId(int page, int size, Long driverUserId);

    boolean changeStatusById(String changedStatus, long cruiseId);

    boolean addNote(long cruiseId, String note);
}
