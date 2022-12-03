package uz.motordepot.mappers;

import uz.motordepot.entity.Car;
import uz.motordepot.entity.Driver;
import uz.motordepot.entity.User;
import uz.motordepot.entity.enums.DriverStatus;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.payload.CarDTO;
import uz.motordepot.payload.DriverDTO;
import uz.motordepot.payload.UserDTO;

import java.util.List;

/**
 * Me: muhammadqodir
 * Project: motor-depot2/IntelliJ IDEA
 * Date:Mon 07/11/22 20:58
 */
public class DriverMapper implements BaseMapper<
        Driver,
        DriverDTO
        > {

    private final CarMapper carMapper = InstanceHolder.getInstance(CarMapper.class);
    private final UserMapper userMapper = InstanceHolder.getInstance(UserMapper.class);

    @Override
    public DriverDTO toDto(Driver entity) {
        CarDTO carDTO = carMapper.toDto(entity.getCar());
        UserDTO userDTO = userMapper.toNameDto(entity.getUser());
        return new DriverDTO(entity.getId(),
                carDTO,
                userDTO,
                entity.getStatus().name(),
                entity.getAddedBy().getFirstName(),
                entity.getAddedAt());
    }

    @Override
    public Driver fromDto(DriverDTO driverDTO) {
        Car car = carMapper.fromDto(driverDTO.getCar());
        User user = userMapper.fromDto(driverDTO.getUser());
        return new Driver(car, user, DriverStatus.define(driverDTO.getStatus()));
    }

    @Override
    public List<Driver> fromDto(List<DriverDTO> dto) {
        return null;
    }

    @Override
    public List<DriverDTO> toDto(List<Driver> entities) {
        return null;
    }
}
