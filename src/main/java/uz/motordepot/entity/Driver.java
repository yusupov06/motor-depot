package uz.motordepot.entity;

import lombok.*;
import uz.motordepot.entity.abs.AbsLongEntity;
import uz.motordepot.entity.enums.DriverStatus;

/**
 * Me: muhammadqodir
 * Project: Motor-depot/IntelliJ IDEA
 * Date:Wed 02/11/22 16:34
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Driver extends AbsLongEntity {
    private Car car;
    private User user;
    private DriverStatus status;
}
