package uz.motordepot.mappers;

import java.util.List;

/**
 * Me: muhammadqodir
 * Project: simple-chat-app/IntelliJ IDEA
 * Date:Sun 06/11/22 21:37
 */
public interface BaseMapper<
        E,
        DTO
        > {

    DTO toDto(E entity);

    E fromDto(DTO dto);

    List<E> fromDto(List<DTO> dto);

    List<DTO> toDto(List<E> entities);
}
