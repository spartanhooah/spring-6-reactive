package net.frey.spring6reactive.service;

import lombok.RequiredArgsConstructor;
import net.frey.spring6reactive.mapper.CustomerMapper;
import net.frey.spring6reactive.model.CustomerDTO;
import net.frey.spring6reactive.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public Flux<CustomerDTO> listCustomers() {
        return repository.findAll().map(mapper::entityToDto);
    }

    @Override
    public Mono<CustomerDTO> getById(int id) {
        return repository.findById(id).map(mapper::entityToDto);
    }

    @Override
    public Mono<CustomerDTO> saveCustomer(CustomerDTO newCustomer) {
        return repository.save(mapper.dtoToEntity(newCustomer)).map(mapper::entityToDto);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(int id, CustomerDTO customer) {
        return repository
                .findById(id)
                .map(foundCustomer -> {
                    foundCustomer.setName(customer.getName());

                    return foundCustomer;
                })
                .flatMap(repository::save)
                .map(mapper::entityToDto);
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(int id, CustomerDTO customer) {
        return repository
                .findById(id)
                .map(foundCustomer -> {
                    if (customer.getName() != null) {
                        foundCustomer.setName(customer.getName());
                    }

                    return foundCustomer;
                })
                .flatMap(repository::save)
                .map(mapper::entityToDto);
    }
}
