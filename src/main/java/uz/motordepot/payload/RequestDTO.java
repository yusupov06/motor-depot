package uz.motordepot.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.motordepot.entity.enums.RequestStatus;

import java.io.Serializable;
import java.time.LocalDateTime;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class RequestDTO implements Serializable {

    private Long id;
    private String name;
    private String from;
    private String to;
    private LocalDateTime addedAt;
    private String characteristics;
    private RequestStatus status;
    private String addedBy;

}
