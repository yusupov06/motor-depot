package uz.motordepot.entity.abs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.motordepot.entity.User;

/**
 * Me: muhammadqodir
 * Project: Motor-depot/IntelliJ IDEA
 * Date:Wed 02/11/22 16:30
 */


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbsLongEntity extends AbsEntity{

    private User addedBy;
    public AbsLongEntity(Long id){
        super(id);
    }

}
