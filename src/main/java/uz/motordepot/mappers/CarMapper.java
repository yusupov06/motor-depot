package uz.motordepot.mappers;

import uz.motordepot.entity.Car;
import uz.motordepot.entity.enums.CarCondition;
import uz.motordepot.entity.enums.CarModel;
import uz.motordepot.payload.CarAddDto;
import uz.motordepot.payload.CarDTO;

import java.util.List;

public class CarMapper implements BaseMapper<
        Car,
        CarDTO
        > {


    @Override
    public CarDTO toDto(Car entity) {
        if (entity == null) return null;
        return new CarDTO(entity.getId(),
                entity.getCarModel().name(),
                entity.getCarNumber(),
                entity.getCondition().name(),
                entity.getCharacteristics(),
                entity.getAddedBy().getFirstName(),
                entity.getAddedAt());
    }

    @Override
    public Car fromDto(CarDTO carDTO) {
        return new Car(carDTO.getId(), CarModel.valueOf(carDTO.getCarModel()),
                carDTO.getCarNumber(), CarCondition.define(carDTO.getCondition()));
    }

    @Override
    public List<Car> fromDto(List<CarDTO> dto) {
        return dto
                .stream()
                .map(this::fromDto)
                .toList();
    }

    @Override
    public List<CarDTO> toDto(List<Car> entities) {
        return entities
                .stream()
                .map(this::toDto)
                .toList();
    }

    public Car fromAddDto(CarAddDto carAddDto) {
        Car car = new Car();
        car.setCarModel(CarModel.define(carAddDto.getCarModel()));
        car.setCarNumber(carAddDto.getCarNumber());
        car.setCharacteristics(carAddDto.getCharacteristics());
        return car;
    }
}
