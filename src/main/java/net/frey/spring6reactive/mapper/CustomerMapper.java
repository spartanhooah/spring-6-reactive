package net.frey.spring6reactive.mapper;

import net.frey.spring6reactive.domain.Customer;
import net.frey.spring6reactive.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer dtoToEntity(CustomerDTO dto);

    CustomerDTO entityToDto(Customer entity);
}
