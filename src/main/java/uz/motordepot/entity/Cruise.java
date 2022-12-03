package uz.motordepot.entity;

import lombok.*;
import uz.motordepot.entity.abs.AbsLongEntity;
import uz.motordepot.entity.enums.CruiseStatus;

/**
 * Me: muhammadqodir
 * Project: Motor-depot/IntelliJ IDEA
 * Date:Wed 02/11/22 16:18
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Cruise extends AbsLongEntity {

    private Driver driver;
    private Request request;
    private CruiseStatus status;

    public Cruise(Driver driver, Request request, CruiseStatus status, User addedBy){
        super(addedBy);
        this.status = status;
        this.driver = driver;
        this.request = request;
    }

}
