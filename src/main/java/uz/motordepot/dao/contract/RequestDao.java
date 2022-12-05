package uz.motordepot.dao.contract;

import uz.motordepot.entity.Request;
import uz.motordepot.entity.enums.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface RequestDao extends Dao<Request, Long> {

    String ID = "id";
    String NAME = "name";
    String FROM = "from_";
    String TO = "to_";
    String CHARACTERISTICS = "characteristics";
    String ADDED_AT = "added_at";
    String ADDED_BY = "added_by";
    String STATUS = "status";
    String UPDATE_QUERY = "update request set name = ?, from_ = ?, to_ = ?, status = ?, characteristics = ? where id = ?;";
    String INSERT_QUERY = "INSERT INTO request(name, from_, to_, status, characteristics, added_by) VALUES (?,?,?,?,?,?);";
    String DELETE_BY_ID_QUERY = "delete FROM request WHERE id = ?;";
    String FIND_BY_ID_QUERY = "SELECT * FROM request where id = ?;";
    String FIND_BY_ID_AND_ADDER_QUERY = "SELECT * FROM request where id = ? and added_by = ?;";
    String FIND_ALL_QUERY = "SELECT * from request ;";
    String FIND_STATUS_BY_ID = "SELECT status from request where id = ?;";
    String FIND_TOTAL_COUNT_QUERY = "SELECT count(id) as total from request;";
    String FIND_TOTAL_COUNT_QUERY_BY_STATUS = "SELECT count(id) as total from request where status = ?;";
    String FIND_TOTAL_COUNT_QUERY_BY_ADDER_ID = "SELECT count(id) as total from request where added_by = ?;";
    String FIND_ALL_BY_PAGE_QUERY = "SELECT * from request order by id desc offset ? limit ?;";
    String FIND_ALL_BY_PAGE_AND_STATUS_QUERY = "SELECT * from request where status = ? order by id desc offset ? limit ?;";
    String FIND_ALL_BY_PAGE_AND_ADDER_ID = "SELECT * from request where added_by = ? order by id desc offset ? limit ?;";
    String EXIST_BY_ID = "SELECT id FROM request where id = ?";

    /**
     * Finding List of entities with this size and status
     * @param page that we want to find
     * @param size count of entities for per page
     * @param status status of entities
     * @return List of entities with this size and status
     */
    List<Request> findPageByStatus(int page, int size, RequestStatus status);

    /**
     * Finding status of entity with this id
     * @param id
     * @return status of entity
     */
    String getStatusById(Long id);

    int findTotalPagesByAdderId(int size, Long adderId);

    List<Request> findPageByAdderId(int page, int pageCount, Long adderId);

    Optional<Request> findByIdAndAdder(Long id, Long addedBy);
}
