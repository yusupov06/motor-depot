package uz.motordepot.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarDTO {

    private Long id;
    private String carModel;
    private String carNumber;
    private String condition;
    private String addedBy;
    private LocalDateTime addedAt;

}
