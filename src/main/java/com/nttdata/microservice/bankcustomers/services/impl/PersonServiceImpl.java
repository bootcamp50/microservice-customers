package com.nttdata.microservice.bankcustomers.services.impl;

import java.util.Date;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nttdata.microservice.bankcustomers.collections.PersonCollection;
import com.nttdata.microservice.bankcustomers.collections.enums.PersonTypeEnum;
import com.nttdata.microservice.bankcustomers.repository.IPersonRepository;
import com.nttdata.microservice.bankcustomers.services.IPersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements IPersonService{
	
	@Autowired
	private IPersonRepository repository;

	@Override
	public Mono<PersonCollection> saveCustomerPersonal(PersonCollection collection) {
		collection.setCode(UUID.randomUUID().toString());
		collection.setPersonType(PersonTypeEnum.PERSONAL.toString());
		collection.setCreatedAt(new Date());
		return repository.save(collection);
	}

	@Override
	public Mono<PersonCollection> saveCustomerEnterprise(PersonCollection collection) {
		collection.setCode(UUID.randomUUID().toString());
		collection.setPersonType(PersonTypeEnum.ENTERPRISE.toString());
		collection.setCreatedAt(new Date());
		return repository.save(collection);
	}

	@Override
	public Flux<PersonCollection> list() {
		return repository.findAll();
	}

	@Override
	public Mono<Boolean> checkIfCustomerExist(String code) {
		return repository.findByCode(code).next().flatMap(collection -> {
			return Mono.just(collection!=null?true:false);
		} );
	}

	@Override
	public Mono<Boolean> checkIfCustomerPersonal(String code) {
		return repository.findByCode(code).next().flatMap(collection -> {
			return Mono.just(collection.getPersonType().equals(PersonTypeEnum.PERSONAL.toString())?true:false);
		} );
	}

	@Override
	public Mono<Boolean> checkIfCustomerEnterprise(String code) {
		return repository.findByCode(code).next().flatMap(collection -> {
			return Mono.just(collection.getPersonType().equals(PersonTypeEnum.ENTERPRISE.toString())?true:false);
		} );
	}

	@Override
	public Mono<String> findByCode(String code) {
		return repository.findByCode(code).next().map(x->x.getCode());
	}

	@Cacheable(value = "personCache")
	@Override
	public Mono<String> findByCodeCached(String code) {
		return repository.findByCode(code).next().map(x->x.getCode()).cache();
	}

	@Override
	public Mono<PersonCollection> updateComment(String personCode, String comment) {
		return repository.findByCode(personCode).next().flatMap(collection -> {
			collection.setComment(comment);
			return repository.save(collection);
		});
	}
}
