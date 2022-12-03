package uz.motordepot.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Me: muhammadqodir
 * Project: motor-depot2/IntelliJ IDEA
 * Date:Mon 07/11/22 21:29
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestAddDTO {

    private String name;
    private String from;
    private String to;
    private long addedBy;

}
