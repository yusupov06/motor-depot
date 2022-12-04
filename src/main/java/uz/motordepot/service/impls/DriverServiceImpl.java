package uz.motordepot.service.impls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.motordepot.dao.contract.CarDao;
import uz.motordepot.dao.contract.DriverDao;
import uz.motordepot.dao.contract.UserDao;
import uz.motordepot.entity.Car;
import uz.motordepot.entity.Driver;
import uz.motordepot.entity.Role;
import uz.motordepot.entity.User;
import uz.motordepot.entity.enums.DriverStatus;
import uz.motordepot.entity.enums.UserRole;
import uz.motordepot.entity.enums.UserStatus;
import uz.motordepot.exception.ServiceException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.mappers.DriverMapper;
import uz.motordepot.pagination.Page;
import uz.motordepot.pagination.PageRequest;
import uz.motordepot.payload.DriverDTO;
import uz.motordepot.payload.RegisterDriverDTO;
import uz.motordepot.service.contract.DriverService;
import uz.motordepot.utils.Utils;
import uz.motordepot.utils.encoder.PasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class DriverServiceImpl implements DriverService {

    private final Logger logger = LogManager.getLogger();
    private final DriverMapper driverMapper = InstanceHolder.getInstance(DriverMapper.class);
    private final UserDao userDao = InstanceHolder.getInstance(UserDao.class);
    private final CarDao carDao = InstanceHolder.getInstance(CarDao.class);
    private final DriverDao dao = InstanceHolder.getInstance(DriverDao.class);
    private final PasswordEncoder passwordEncoder = InstanceHolder.getInstance(PasswordEncoder.class);

    @Override
    public boolean add(RegisterDriverDTO registerDriverDTO) {
        logger.info("DRIVER ADDING METHOD");
        Driver driver;
        try {
            driver = fromDriverRegisterDTO(registerDriverDTO);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error while adding request");
            throw ServiceException.throwExc(e.getMessage());
        }
        dao.save(driver);
        return true;
    }

    @Override
    public boolean edit(Long id, RegisterDriverDTO registerDriverDTO) {
        logger.info("EDIT DRIVER METHOD CALLED");
        Driver driver = dao.findById(id)
                .orElseThrow(() -> ServiceException.throwExc("Driver not found with id " + id, 404));
        try {
            driver = fromDriverEditDTO(driver, registerDriverDTO);
            dao.save(driver);
            return true;
        } catch (NoSuchAlgorithmException e) {
            logger.error("ERROR WHILE EDITING");
            throw ServiceException.throwExc(e.getMessage());
        }
    }

    @Override
    public List<DriverDTO> getAllDrivers() {
        logger.info("get all DRIVERS METHOD CALLED");
        return dao
                .findAll()
                .stream()
                .map(driverMapper::toDto)
                .toList();
    }

    @Override
    public Page<DriverDTO> findAllByPage(int page, int pageCount) {

        logger.info("FIND ALL DRIVER BY PAGE METHOD CALLED");
        PageRequest pageRequest = new PageRequest(page, pageCount);

        int totalPages = dao.findTotalPages(pageCount, DriverDao.TOTAL_COUNT_QUERY);

        List<DriverDTO> list = dao
                .findByPage(page, pageCount)
                .stream()
                .map(driverMapper::toDto)
                .toList();

        return Utils.getPage(pageRequest.getPage(), totalPages, list);
    }

    @Override
    public Page<DriverDTO> findPageByStatus(int page, int size, DriverStatus status) {

        logger.info("FIND ALL BY PAGINATION METHOD CALLED");
        PageRequest pageRequest = new PageRequest(page, size);

        int totalPages = dao.findTotalPagesByStatus(size, DriverDao.TOTAL_COUNT_QUERY_WHERE_STATUS, status.name());

        List<DriverDTO> byPageAndStatus = dao.findByPageAndStatus(page, size, status.name())
                .stream()
                .map(driverMapper::toDto)
                .toList();

        return Utils.getPage(pageRequest.getPage(), totalPages, byPageAndStatus);
    }

    @Override
    public Optional<DriverDTO> findById(long driverId) {

        logger.info("FIND DRIVER BY ID {}", driverId);
        return dao
                .findById(driverId).map(driverMapper::toDto);
    }

    @Override
    public DriverStatus getStatusById(Long id) {
        logger.info("GET DRIVER STATUS WITH ID {}", id);
        return DriverStatus.define(dao.findStatusById(id));
    }

    @Override
    public boolean existsById(long id) {
        return dao.existsById(id, DriverDao.EXIST_BY_ID);
    }

    @Override
    public void delete(Long id) {
        logger.info("DELETE DRIVER WITH ID {}", id);
        dao.delete(id);
    }

    @Override
    public List<DriverDTO> findAll() {
        logger.info("FINDING ALL DRIVERS METHOD CALLED");
        return dao
                .findAll()
                .stream()
                .map(driverMapper::toDto)
                .toList();
    }

    @Override
    public Page<DriverDTO> findByPage(int page, int size) {

        logger.info("FIND DRIVERS BY PAGINATION METHOD CALLED");
        PageRequest pageRequest = new PageRequest(page, size);

        int totalPages = dao.findTotalPages(size, DriverDao.TOTAL_COUNT_QUERY);

        List<DriverDTO> byPage = dao.findByPage(page, size)
                .stream()
                .map(driverMapper::toDto)
                .toList();

        return Utils.getPage(pageRequest.getPage(), totalPages, byPage);

    }

    public Driver fromDriverRegisterDTO(RegisterDriverDTO rd) throws NoSuchAlgorithmException {
        if (rd == null) return null;

        User user = new User(rd.getFirstName(),
                rd.getLastName(), rd.getPhoneNumber(),
                passwordEncoder.encode(rd.getPassword()), UserStatus.ACTIVE, new Role(1L, UserRole.DRIVER, ""));

        Car car;
        if (carDao.existsById(rd.getCarId(), CarDao.EXISTS_BY_ID)) {
            car = new Car();
            car.setId(rd.getCarId());
        } else
            throw ServiceException.throwExc("Car not found with id " + rd.getCarId(), 404);
        User adder;
        if (userDao.existsById(rd.getAddedBy(), UserDao.EXIST_BY_ID)) {
            adder = new User();
            adder.setId(rd.getAddedBy());
        } else
            throw ServiceException.throwExc("Adder not found with id " + rd.getAddedBy(), 404);

        Driver build = Driver.builder()
                .car(car)
                .user(user)
                .status(DriverStatus.FREE)
                .build();

        build.setAddedBy(adder);
        return build;
    }

    public Driver fromDriverEditDTO(Driver driver, RegisterDriverDTO rd) throws NoSuchAlgorithmException {

        if (rd == null) return null;

        User user = driver.getUser();
        user.setFirstName(rd.getFirstName());
        user.setLastName(rd.getLastName());
        user.setPhoneNumber(rd.getPhoneNumber());
        if (Objects.nonNull(rd.getPassword()))
            user.setPassword(passwordEncoder.encode(rd.getPassword()));
        if (!driver.getCar().getId().equals(rd.getCarId())) {
            Car car;
            if (carDao.existsById(rd.getCarId(), CarDao.EXISTS_BY_ID)) {
                car = new Car();
                car.setId(rd.getCarId());
            } else
                throw ServiceException.throwExc("Car not found with id " + rd.getCarId(), 404);
            driver.setCar(car);
        }

        return driver;
    }

}
