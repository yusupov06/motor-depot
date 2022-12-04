package uz.motordepot.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDriverDTO {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private long carId;
    private long addedBy;
    public RegisterDriverDTO(String firstName, String lastName, String password, String phoneNumber, long carId) {
        this(firstName, lastName, phoneNumber, carId);
        this.password = password;
    }

    public RegisterDriverDTO(String firstName, String lastName, String phoneNumber, long carId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.carId = carId;
    }
}
