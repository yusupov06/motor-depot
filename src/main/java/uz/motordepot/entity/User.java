package uz.motordepot.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.motordepot.entity.abs.AbsEntity;
import uz.motordepot.entity.enums.UserStatus;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends AbsEntity {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private UserStatus status;
    private Role role;

    public User(Long id, String firstName,
                String lastName, String phoneNumber,
                UserStatus status) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public User(String firstName, String lastName, String phoneNumber,
                String password, UserStatus status, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.status = status;
        this.role = role;
    }
}
