package uz.motordepot.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.motordepot.entity.abs.AbsLongEntity;
import uz.motordepot.entity.enums.CarCondition;
import uz.motordepot.entity.enums.CarModel;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class Car extends AbsLongEntity {

    private CarModel carModel;
    private String carNumber;
    private CarCondition condition;

    public Car(Long id, CarModel carModel, String carNumber, CarCondition condition) {
        super(id);
        this.carModel = carModel;
        this.carNumber = carNumber;
        this.condition = condition;
    }

    public Car(CarModel carModel, String carNumber, CarCondition condition) {
        this.carModel = carModel;
        this.carNumber = carNumber;
        this.condition = condition;
    }

}
