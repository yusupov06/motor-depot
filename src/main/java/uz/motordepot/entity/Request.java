package uz.motordepot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.motordepot.entity.abs.AbsLongEntity;
import uz.motordepot.entity.enums.RequestStatus;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Request extends AbsLongEntity {

    private String name;
    private String from;
    private String to;
    private RequestStatus status;
//    private LocalDateTime startTime;

}
