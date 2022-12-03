package uz.motordepot.service.contract;

import uz.motordepot.payload.UserAddDTO;
import uz.motordepot.payload.UserDTO;

import java.util.Optional;

/**
 * Me: muhammadqodir
 * Project: motor-depot2/IntelliJ IDEA
 * Date:Mon 07/11/22 21:13
 */
public interface UserService extends CRUDService<UserAddDTO, UserDTO> {

    Optional<UserDTO> authenticate(String email, String password);

}
