package uz.motordepot.mappers;

import uz.motordepot.entity.User;
import uz.motordepot.entity.enums.PermissionEnum;
import uz.motordepot.entity.enums.UserStatus;
import uz.motordepot.payload.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Me: muhammadqodir
 * Project: motor-depot2/IntelliJ IDEA
 * Date:Sat 05/11/22 09:17
 */
public class UserMapper implements BaseMapper<
        User,
        UserDTO
        > {

    @Override
    public UserDTO toDto(User user) {
        if (user == null) return null;
        return new UserDTO(user.getId(), user.getFirstName(),
                user.getLastName(), user.getPhoneNumber(),
                user.getStatus().name(), user.getRole().getName().name(),
                user.getRole()
                        .getPermissions()
                        .stream()
                        .map(PermissionEnum::name)
                        .collect(Collectors.toSet()));
    }

    @Override
    public User fromDto(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(),
                userDTO.getPhoneNumber(), UserStatus.define(userDTO.getStatus()));
    }

    @Override
    public List<User> fromDto(List<UserDTO> dto) {
        return dto
                .stream()
                .map(this::fromDto)
                .toList();
    }

    @Override
    public List<UserDTO> toDto(List<User> entities) {
        return entities
                .stream()
                .map(this::toDto)
                .toList();
    }

    public UserDTO toNameDto(User user) {
        if (user == null) return null;
        return new UserDTO(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber());
    }
}
