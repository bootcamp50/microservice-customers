package com.nttdata.microservice.bankcustomers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.microservice.bankcustomers.collections.PersonCollection;
import com.nttdata.microservice.bankcustomers.dto.PersonDto;
import com.nttdata.microservice.bankcustomers.exceptions.CustomerDuplicatedException;
import com.nttdata.microservice.bankcustomers.services.IPersonService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping(value = "/person")
public class PersonController {
	
	@Autowired
	private IPersonService personService;

	@GetMapping(value = "/getCustomers")
	public Flux<PersonDto> getCustomers() throws Exception{
		return personService.list();
	}
	
	@PostMapping(value = "/saveCustomer")
	public Mono<ResponseEntity<PersonCollection>> saveCustomer(@RequestBody PersonDto dto){
		log.info("save customer");
        return personService.create(dto)
                .flatMap(createdCustomer -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer)))
                .onErrorResume(CustomerDuplicatedException.class, error -> Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build()))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@GetMapping("/getCustomer/{id}")
    public Mono<ResponseEntity<PersonDto>> findCustomerById(@PathVariable("id") String id) {
        log.info("Get operation in /getCustomer/{}", id);
        return personService.findById(id)
                .flatMap(retrievedCustomer -> Mono.just(ResponseEntity.ok(retrievedCustomer)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
	
	@GetMapping("/getCustomerCached/{id}")
    public Mono<ResponseEntity<PersonDto>> findCustomerByIdCached(@PathVariable("id") String id) {
        log.info("Get operation in /getCustomerCached/{}", id);
        return personService.findByIdCached(id)
                .flatMap(retrievedCustomer -> Mono.just(ResponseEntity.ok(retrievedCustomer)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
	
}
