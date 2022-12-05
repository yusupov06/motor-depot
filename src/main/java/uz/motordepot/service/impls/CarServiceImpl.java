package uz.motordepot.service.impls;

import uz.motordepot.dao.contract.CarDao;
import uz.motordepot.dao.contract.UserDao;
import uz.motordepot.entity.Car;
import uz.motordepot.entity.User;
import uz.motordepot.entity.enums.CarCondition;
import uz.motordepot.entity.enums.CarModel;
import uz.motordepot.exception.ServiceException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.mappers.CarMapper;
import uz.motordepot.pagination.Page;
import uz.motordepot.pagination.PageRequest;
import uz.motordepot.payload.CarAddDto;
import uz.motordepot.payload.CarDTO;
import uz.motordepot.service.contract.CarService;
import uz.motordepot.utils.Utils;

import java.util.List;

public class CarServiceImpl implements CarService {

    private final CarDao carDao = InstanceHolder.getInstance(CarDao.class);
    private final UserDao userDao = InstanceHolder.getInstance(UserDao.class);
    private final CarMapper mapper = InstanceHolder.getInstance(CarMapper.class);

    @Override
    public boolean add(CarAddDto carAddDto) {
        Car car = fromCDto(carAddDto);
        carDao.save(car);
        return true;
    }

    public Car fromCDto(CarAddDto carAddDto) {
        if (userDao.existsById(carAddDto.getAddedBy(), UserDao.EXISTS_BY_ID)) {
            Car build = Car.builder()
                    .carNumber(carAddDto.getCarNumber())
                    .carModel(CarModel.define(carAddDto.getCarModel()))
                    .build();
            User user = new User();
            user.setId(carAddDto.getAddedBy());
            build.setAddedBy(user);
            return build;
        } else
            throw ServiceException.throwExc("ADDER NOT FOUND WITH ID " + carAddDto.getAddedBy(), 404);
    }

    @Override
    public boolean edit(Long id, CarAddDto carAddDto) {

        Car car = carDao
                .findById(id)
                .orElseThrow(
                        () -> ServiceException
                                .throwExc("Car not found", 404));
        car.setCarModel(CarModel.define(carAddDto.getCarModel()));
        car.setCarNumber(carAddDto.getCarNumber());
        return carDao.save(car);
    }

    @Override
    public void delete(Long id) {
        carDao.delete(id);
    }

    @Override
    public List<CarDTO> findAll() {
        return carDao
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Page<CarDTO> findByPage(int page, int size) {

        PageRequest pageRequest = new PageRequest(page, size);

        int totalPages = carDao.findTotalPages(pageRequest.getSize(), CarDao.TOTAL_COUNT_QUERY);

        List<CarDTO> allByPage = carDao
                .findByPage(pageRequest.getPage(), pageRequest.getSize())
                .stream()
                .map(mapper::toDto)
                .toList();

        return Utils.getPage(pageRequest.getPage(), totalPages, allByPage);
    }

    @Override
    public CarDTO findById(Long carId) {
        return mapper.toDto(carDao
                .findById(carId)
                .orElseThrow(() -> ServiceException.throwExc("Car not found", 404)));
    }

    @Override
    public Page<CarDTO> findByPageAndStatus(int page, int size, String condition) {
        PageRequest pageRequest = new PageRequest(page, size);

        int totalPages = carDao.findTotalPagesByCondition(condition, pageRequest.getSize());

        List<CarDTO> allByPage = carDao
                .findByPageAndCondition(pageRequest.getPage(), pageRequest.getSize(), CarCondition.NOT_ACTIVE.name())
                .stream()
                .map(mapper::toDto)
                .toList();

        return Utils.getPage(pageRequest.getPage(), totalPages, allByPage);
    }

    @Override
    public CarCondition getStatusById(Long carId) {
        return CarCondition.define(carDao.findConditionById(carId));
    }
}
