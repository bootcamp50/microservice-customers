package com.nttdata.microservice.bankcustomers.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.microservice.bankcustomers.collections.PersonCollection;

import reactor.core.publisher.Flux;

@Repository
public interface IPersonRepository extends ReactiveCrudRepository<PersonCollection, ObjectId>{
	public Flux<PersonCollection> findByCode(String code);
}
