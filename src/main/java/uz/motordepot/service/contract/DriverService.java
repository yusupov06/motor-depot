package uz.motordepot.service.contract;

import uz.motordepot.entity.enums.DriverStatus;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.DriverDTO;
import uz.motordepot.payload.RegisterDriverDTO;

import java.util.List;
import java.util.Optional;


public interface DriverService extends CRUDService<RegisterDriverDTO ,DriverDTO> {

    List<DriverDTO> getAllDrivers();

    Page<DriverDTO> findAllByPage(int page, int pageCount);

    Page<DriverDTO> findPageByStatus(int page, int size, DriverStatus status);

    Optional<DriverDTO> findById(long driverId);

    DriverStatus getStatusById(Long id);

    boolean existsById(long id);
}
