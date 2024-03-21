package com.nttdata.microservice.bankcustomers.services;

import com.nttdata.microservice.bankcustomers.collections.PersonCollection;
import com.nttdata.microservice.bankcustomers.dto.PersonDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPersonService {
	
	//public Mono<PersonCollection> saveCustomerPersonal(PersonCollection collection);
	//public Mono<PersonCollection> saveCustomerEnterprise(PersonCollection collection);
	public Mono<PersonCollection> create(PersonDto personDto);
	
	public Flux<PersonDto> list();
	
	/*public Mono<Boolean> checkIfCustomerExist(String code);
	public Mono<Boolean> checkIfCustomerPersonal(String code);
	public Mono<Boolean> checkIfCustomerEnterprise(String code);*/
	
	public Mono<PersonDto> findById(String id);
	public Mono<PersonDto> findByIdCached(String id);
	
	//public Mono<String> findByCode(String code);
	

	//public Mono<String> findByCodeCached(String code);
	
	public Mono<PersonCollection> updateComment(String personCode, String code);
	
}
