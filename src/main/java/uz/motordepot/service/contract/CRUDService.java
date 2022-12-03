package uz.motordepot.service.contract;

import uz.motordepot.pagination.Page;

import java.util.List;

/**
 * {@link CRUDService} for all services as CRUD
 * @param <AddDto>
 * @param <Dto>
 */
public interface CRUDService<
        AddDto,
        Dto
        > {

    boolean add(AddDto dto);

    boolean edit(Long id, AddDto dto);

    void delete(Long id);

    List<Dto> findAll();
    Page<Dto> findByPage(int page, int size);

}
