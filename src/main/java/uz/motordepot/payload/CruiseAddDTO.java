package uz.motordepot.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Me: muhammadqodir
 * Project: motor-depot2/IntelliJ IDEA
 * Date:Mon 07/11/22 21:25
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CruiseAddDTO {

    private Long driverId;
    private Long requestId;
    private Long addedBy;

}
