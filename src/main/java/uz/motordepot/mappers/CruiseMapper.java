package uz.motordepot.mappers;

import uz.motordepot.entity.Cruise;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.payload.CruiseDTO;

import java.util.List;

/**
 * Me: muhammadqodir
 * Project: motor-depot2/IntelliJ IDEA
 * Date:Mon 07/11/22 16:47
 */
public class CruiseMapper implements BaseMapper<
        Cruise,
        CruiseDTO
        > {
    private final DriverMapper driverMapper = InstanceHolder.getInstance(DriverMapper.class);
    private final RequestMapper requestMapper = InstanceHolder.getInstance(RequestMapper.class);

    @Override
    public CruiseDTO toDto(Cruise entity) {
        return new CruiseDTO(entity.getId(),
                driverMapper.toDto(entity.getDriver()),
                requestMapper.toDto(entity.getRequest()),
                entity.getStatus().name(),
                entity.getNote(),
                entity.getAddedAt());
    }

    @Override
    public Cruise fromDto(CruiseDTO cruiseDTO) {
        return null;
    }

    @Override
    public List<Cruise> fromDto(List<CruiseDTO> dto) {
        return null;
    }

    @Override
    public List<CruiseDTO> toDto(List<Cruise> entities) {
        return null;
    }
}
