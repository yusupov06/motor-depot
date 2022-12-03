package uz.motordepot.service.contract;

import uz.motordepot.entity.enums.CarCondition;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.CarAddDto;
import uz.motordepot.payload.CarDTO;

public interface CarService extends CRUDService<
        CarAddDto,
        CarDTO
        >{

    CarDTO findById(Long carId);

    Page<CarDTO> findByPageAndStatus(int page, int size, String condition);

    CarCondition getStatusById(Long carId);
}
