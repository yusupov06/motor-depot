package uz.motordepot.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Me: muhammadqodir
 * Project: motor-depot2/IntelliJ IDEA
 * Date:Sat 05/11/22 09:10
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private UserDTO user;
    private boolean hasError;
    private String error;
    private boolean loggedIn;

}
