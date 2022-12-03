package uz.motordepot.service.contract;

import uz.motordepot.entity.enums.CruiseStatus;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.CruiseAddDTO;
import uz.motordepot.payload.CruiseDTO;

import java.util.List;
import java.util.Optional;

public interface CruiseService extends CRUDService<CruiseAddDTO, CruiseDTO> {

    List<CruiseDTO> getAllForManager(Long managerId);

    List<CruiseDTO> getAllForDriver(Long driverId);

    List<CruiseDTO> findAllByDriverId(Long id);

    CruiseStatus getStatusById(Long id);

    Page<CruiseDTO> findByPageAndDriver(int page, int pageCount, Long driverUserId);

    Optional<CruiseDTO> findById(long id);
}
