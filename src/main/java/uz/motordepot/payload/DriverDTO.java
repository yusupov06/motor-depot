package uz.motordepot.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Me: muhammadqodir
 * Project: motor-depot2/IntelliJ IDEA
 * Date:Mon 07/11/22 18:18
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverDTO {
    private long id;
    private CarDTO car;
    private UserDTO user;
    private String status;
    private String addedBy;
    private LocalDateTime addedAt;
}
