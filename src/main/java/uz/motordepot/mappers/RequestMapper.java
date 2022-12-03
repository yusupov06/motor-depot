package uz.motordepot.mappers;

import org.jetbrains.annotations.NotNull;
import uz.motordepot.entity.Request;
import uz.motordepot.payload.RequestDTO;

import java.util.List;

public class RequestMapper implements BaseMapper<
        Request,
        RequestDTO
        > {

    @Override
    public RequestDTO toDto(@NotNull Request request) {
        return new RequestDTO(request.getId(), request.getName(),
                request.getFrom(), request.getTo(),
                request.getAddedAt(),
                request.getStatus(),
                request.getAddedBy().getFirstName());
    }

    @Override
    public Request fromDto(RequestDTO requestDTO) {
        return null;
    }

    @Override
    public List<Request> fromDto(List<RequestDTO> dto) {
        return null;
    }

    @Override
    public List<RequestDTO> toDto(List<Request> entities) {
        return null;
    }
}
