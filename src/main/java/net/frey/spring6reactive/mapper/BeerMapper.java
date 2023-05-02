package net.frey.spring6reactive.mapper;

import net.frey.spring6reactive.domain.Beer;
import net.frey.spring6reactive.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer dtoToEntity(BeerDTO dto);

    BeerDTO entityToDto(Beer entity);
}
