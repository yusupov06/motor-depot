package uz.motordepot.dao.contract;

import uz.motordepot.entity.Car;

import java.util.List;

public interface CarDao extends Dao<Car, Long> {

    String ID = "id";
    String CAR_MODEL = "car_model";
    String CAR_NUMBER = "car_number";
    String CONDITION = "condition";
    String ADDED_AT = "added_at";
    String ADDED_BY = "added_by";

    String UPDATE_QUERY = "update car set car_model = ?, car_number = ?, condition = ? where id = ?;";
    String UPDATE_TO_CONDITION_BY_ID = "update car set condition = ? where id = ?;";
    String INSERT_QUERY = "INSERT INTO CAR(car_model, car_number, condition, added_by) VALUES (?,?,?,?);";
    String DELETE_QUERY = "DELETE FROM car WHERE id = ? and condition = 'NOT_ACTIVE';";
    String FIND_BY_ID_QUERY = "SELECT * FROM car WHERE id = ?;";
    String FIND_ALL_QUERY = "SELECT * FROM car order by id desc;";
    String TOTAL_COUNT_QUERY = "SELECT count(id) as total_pages from car;";
    String TOTAL_COUNT_QUERY_AND_CONDITION = "SELECT count(id) as total_pages from car where condition = ?;";
    String FIND_ALL_BY_PAGE_QUERY = "SELECT * FROM car order by id desc offset ? limit ?;";
    String FIND_ALL_BY_PAGE_AND_CONDITION_QUERY = "SELECT * FROM car where condition = ? order by id desc offset ? limit ? ;";
    String EXISTS_BY_ID = "SELECT id FROM car where id = ?;";
    String FIND_CONDITION_BY_ID = "SELECT condition FROM car where id = ?;";

    /**
     * Finding List of {@link Car}s of which is in this condition
     * @param page for pagination
     * @param size count of {@link Car}s for per page
     * @return List of Cars
     */
    List<Car> findByPageAndCondition(int page, int size, String condition);

    /**
     * Finding total pages of cars which is in this condition
     * @param condition Car Condition
     * @param size count of cars for per page
     * @return count of pages
     */
    int findTotalPagesByCondition(String condition, int size);

    /**
     * Set the car as Active which is in this id
     * @param id
     * @return true if done false otherwise
     */
    boolean setAsActive(Long id);

    /**
     * Finding car condition which is in this id
     * @param carId
     * @return
     */
    String findConditionById(Long carId);
}
