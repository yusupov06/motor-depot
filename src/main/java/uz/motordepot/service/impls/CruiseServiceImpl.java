package uz.motordepot.service.impls;

import uz.motordepot.dao.contract.CruiseDao;
import uz.motordepot.dao.contract.DriverDao;
import uz.motordepot.dao.contract.RequestDao;
import uz.motordepot.dao.contract.UserDao;
import uz.motordepot.entity.Cruise;
import uz.motordepot.entity.Driver;
import uz.motordepot.entity.Request;
import uz.motordepot.entity.User;
import uz.motordepot.entity.enums.CruiseStatus;
import uz.motordepot.exception.ServiceException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.mappers.CruiseMapper;
import uz.motordepot.pagination.Page;
import uz.motordepot.pagination.PageRequest;
import uz.motordepot.payload.CruiseAddDTO;
import uz.motordepot.payload.CruiseDTO;
import uz.motordepot.service.contract.CruiseService;
import uz.motordepot.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CruiseServiceImpl implements CruiseService {


    private final CruiseDao dao = InstanceHolder.getInstance(CruiseDao.class);
    private final DriverDao driverDao = InstanceHolder.getInstance(DriverDao.class);
    private final RequestDao requestDao = InstanceHolder.getInstance(RequestDao.class);
    private final UserDao userDao = InstanceHolder.getInstance(UserDao.class);
    private final CruiseMapper cruiseMapper = InstanceHolder.getInstance(CruiseMapper.class);

    @Override
    public boolean add(CruiseAddDTO dto) {
        Cruise cruise = fromCDto(dto);
        dao.save(cruise);
        return true;
    }

    private Cruise fromCDto(CruiseAddDTO dto) {
        Driver driver;
        Request request;
        User user;
        if (driverDao.existsById(dto.getDriverId(), DriverDao.EXIST_BY_ID)) {
            driver = new Driver();
            driver.setId(dto.getDriverId());
        } else
            throw ServiceException.throwExc("Driver not found with id " + dto.getDriverId(), 404);
        if (requestDao.existsById(dto.getRequestId(), RequestDao.EXIST_BY_ID)) {
            request = new Request();
            request.setId(dto.getRequestId());
        } else
            throw ServiceException.throwExc("Request not found with id " + dto.getRequestId(), 404);
        if (userDao.existsById(dto.getAddedBy(), UserDao.EXISTS_BY_ID)) {
            user = new User();
            user.setId(dto.getAddedBy());
        } else
            throw ServiceException.throwExc("Request not found with id " + dto.getRequestId(), 404);

        return new Cruise(driver, request, CruiseStatus.CREATED, user);
    }

    @Override
    public boolean edit(Long id, CruiseAddDTO dto) {
        Cruise cruise = fromCDto(dto);
        cruise.setId(id);
        dao.save(cruise);
        return true;
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public List<CruiseDTO> findAll() {
        return null;
    }

    @Override
    public Page<CruiseDTO> findByPage(int page, int size) {

        PageRequest pageRequest = new PageRequest(page, size);
        int totalPages = dao.findTotalPages(pageRequest.getSize(), CruiseDao.TOTAL_COUNT_QUERY);

        List<CruiseDTO> cruiseDTOS = dao
                .findByPage(pageRequest.getPage(), pageRequest.getSize())
                .stream()
                .map(cruiseMapper::toDto)
                .toList();

        return Utils.getPage(pageRequest.getPage(), totalPages, cruiseDTOS);
    }

    @Override
    public List<CruiseDTO> getAllForManager(Long managerId) {
        return dao
                .findAllByOwnerId(managerId)
                .stream()
                .map(cruiseMapper::toDto)
                .toList();
    }

    @Override
    public List<CruiseDTO> getAllForDriver(Long driverId) {
        return dao
                .findAllByDriverId(driverId)
                .stream()
                .map(cruiseMapper::toDto)
                .toList();
    }

    @Override
    public List<CruiseDTO> findAllByDriverId(Long id) {
        return dao
                .findAllByDriverId(id)
                .stream()
                .map(cruiseMapper::toDto)
                .toList();
    }

    @Override
    public CruiseStatus getStatusById(Long id) {
        return null;
    }

    @Override
    public Page<CruiseDTO> findByPageAndDriver(int page, int size, Long driverUserId) {

        PageRequest pageRequest = new PageRequest(page, size);
        int totalPages = dao.findTotalPagesWhereDriverId(pageRequest.getSize(), driverUserId);

        List<CruiseDTO> cruiseDTOS = dao
                .findByPageAndDriverUserId(pageRequest.getPage(), pageRequest.getSize(), driverUserId)
                .stream()
                .map(cruiseMapper::toDto)
                .toList();

        return Utils.getPage(pageRequest.getPage(), totalPages, cruiseDTOS);
    }

    @Override
    public Optional<CruiseDTO> findById(long id) {
        return dao.findById(id).map(cruiseMapper::toDto);
    }

    @Override
    public void addNote(long cruiseId, String note) {
        dao.addNote(cruiseId, note);
    }

}
