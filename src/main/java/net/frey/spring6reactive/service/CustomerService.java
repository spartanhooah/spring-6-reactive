package net.frey.spring6reactive.service;

import net.frey.spring6reactive.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerDTO> listCustomers();

    Mono<CustomerDTO> getById(int id);

    Mono<CustomerDTO> saveCustomer(CustomerDTO newCustomer);

    Mono<CustomerDTO> updateCustomer(int id, CustomerDTO customer);

    Mono<CustomerDTO> patchCustomer(int id, CustomerDTO customer);
}
