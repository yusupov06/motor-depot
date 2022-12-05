package uz.motordepot.service.impls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.motordepot.dao.contract.RequestDao;
import uz.motordepot.dao.contract.UserDao;
import uz.motordepot.entity.Request;
import uz.motordepot.entity.User;
import uz.motordepot.entity.enums.RequestStatus;
import uz.motordepot.exception.ServiceException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.mappers.RequestMapper;
import uz.motordepot.pagination.Page;
import uz.motordepot.pagination.PageRequest;
import uz.motordepot.payload.RequestAddDTO;
import uz.motordepot.payload.RequestDTO;
import uz.motordepot.service.contract.RequestService;
import uz.motordepot.utils.Utils;

import java.util.List;
import java.util.Optional;


public class RequestServiceImpl implements RequestService {

    private final Logger logger = LogManager.getLogger();
    private final RequestDao dao = InstanceHolder.getInstance(RequestDao.class);
    private final UserDao userDao = InstanceHolder.getInstance(UserDao.class);
    private final RequestMapper mapper = InstanceHolder.getInstance(RequestMapper.class);

    @Override
    public boolean add(RequestAddDTO dto) {
        logger.info("ADD REQUEST METHOD CALLED");
        Request request = fromCDto(dto);
        dao.save(request);
        return true;
    }

    private Request fromCDto(RequestAddDTO dto) {
        User user;
        if (userDao.existsById(dto.getAddedBy(), UserDao.EXISTS_BY_ID)) {
            user = new User();
            user.setId(dto.getAddedBy());
        } else {
            logger.error("ADDER NOT FOUND WITH ID {}", dto.getAddedBy());
            throw ServiceException.throwExc("Adder not found with id " + dto.getAddedBy(), 404);
        }
        Request request = new Request(dto.getName(), dto.getFrom(), dto.getTo(), RequestStatus.CREATED, dto.getCharacteristics());
        request.setAddedBy(user);
        return request;
    }

    @Override
    public boolean edit(Long id, RequestAddDTO dto) {
        logger.info("EDIT REQUEST METHOD CALLED");
        if (id == null) return false;
        Request request = dao
                .findById(id)
                .orElseThrow(() -> ServiceException.throwExc("Request not found", 404));
        request.setName(dto.getName());
        request.setFrom(dto.getFrom());
        request.setTo(dto.getTo());
        request.setCharacteristics(dto.getCharacteristics());
        return dao.save(request);
    }

    @Override
    public void delete(Long id) {
        logger.info("DELETE REQUEST METHOD CALLED");
        dao.delete(id);
        logger.info("DELETE REQUEST METHOD FINISHED");
    }

    @Override
    public List<RequestDTO> findAll() {
        logger.info("FIND ALL REQUEST METHOD CALLED");
        return dao
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Page<RequestDTO> findByPage(int page, int size) {
        logger.info("FINDING BY PAGINATION METHOD CALLED");
        PageRequest pageRequest = new PageRequest(page, size);

        int totalPages = dao.findTotalPages(pageRequest.getSize(), RequestDao.FIND_TOTAL_COUNT_QUERY);

        List<RequestDTO> requestDTOList = dao
                .findByPage(pageRequest.getPage(), pageRequest.getSize())
                .stream()
                .map(mapper::toDto)
                .toList();
        return Utils.getPage(pageRequest.getPage(), totalPages, requestDTOList);
    }


    @Override
    public RequestDTO findById(Long requestId) {
        logger.info("FINDING USER BY ID METHOD CALLED");
        return mapper
                .toDto(dao
                        .findById(requestId)
                        .orElseThrow(() -> ServiceException
                                .throwExc("REQUEST NOT FOUND WITH ID " + requestId, 404))
                );
    }

    @Override
    public Page<RequestDTO> findPageByStatus(int page, int size, RequestStatus status) {

        logger.info("FINDING REQUESTS WITH STATUS BY PAGINATION METHOD CALLED");
        PageRequest pageRequest = new PageRequest(page, size);
        int totalPages = dao.findTotalPagesByStatus(pageRequest.getSize(),
                RequestDao.FIND_TOTAL_COUNT_QUERY_BY_STATUS, status.name());

        List<RequestDTO> pageByStatus = dao
                .findPageByStatus(page, size, RequestStatus.CREATED)
                .stream()
                .map(mapper::toDto)
                .toList();

        return Utils.getPage(pageRequest.getPage(), totalPages, pageByStatus);
    }

    @Override
    public RequestStatus getStatusById(Long id) {
        logger.info("GETTING REQUEST WITH ID = {}", id);
        return RequestStatus.valueOf(dao.getStatusById(id));
    }

    @Override
    public Page<RequestDTO> findByPageAndAdderId(int page, int pageCount, Long adderId) {

        logger.info("FINDING REQUESTS of which adder is this BY PAGINATION METHOD CALLED");

        PageRequest pageRequest = new PageRequest(page, pageCount);

        int totalPages = dao.findTotalPagesByAdderId(pageRequest.getSize(), adderId);

        List<RequestDTO> pageByStatus = dao
                .findPageByAdderId(page, pageCount, adderId)
                .stream()
                .map(mapper::toDto)
                .toList();

        return Utils.getPage(pageRequest.getPage(), totalPages, pageByStatus);
    }

    @Override
    public RequestDTO getByIdAndAdder(Long id, Long addedBy) {
        logger.info("FINDING STATUS BY ID AND ADDER");
        Optional<Request> byIdAndAdder = dao.findByIdAndAdder(id, addedBy);
        return byIdAndAdder.map(mapper::toDto).orElse(null);

    }
}
