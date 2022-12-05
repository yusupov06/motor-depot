package uz.motordepot.entity.abs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbsEntity {
    private Long id;
    private LocalDateTime addedAt;

    public AbsEntity(Long id) {
        this.id = id;
    }
}
