package uz.motordepot.service.contract;

import uz.motordepot.entity.enums.RequestStatus;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.RequestAddDTO;
import uz.motordepot.payload.RequestDTO;

public interface RequestService extends CRUDService<RequestAddDTO, RequestDTO> {

    RequestDTO findById(Long requestId);

    Page<RequestDTO> findPageByStatus(int page, int size, RequestStatus status);

    RequestStatus getStatusById(Long id);

    Page<RequestDTO> findByPageAndAdderId(int page, int pageCount, Long id);
}
