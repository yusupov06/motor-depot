package uz.motordepot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.motordepot.entity.abs.AbsEntity;
import uz.motordepot.entity.enums.PermissionEnum;
import uz.motordepot.entity.enums.UserRole;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class Role extends AbsEntity {
    private UserRole name;
    private String description;

    private Set<PermissionEnum> permissions;

    public Role(String name) {
        this.name = UserRole.valueOf(name.toUpperCase());
    }

    public Role(Long id, UserRole role, String description) {
        super(id);
        this.name = role;
        this.description = description;
    }
}
