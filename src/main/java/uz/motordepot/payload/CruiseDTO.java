package uz.motordepot.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Me: muhammadqodir
 * Project: motor-depot2/IntelliJ IDEA
 * Date:Mon 07/11/22 18:15
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CruiseDTO {

    private Long id;
    private DriverDTO driver;
    private RequestDTO request;
    private String status;
    private String note;
    private LocalDateTime addedAt;

}
