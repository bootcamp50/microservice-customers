package com.nttdata.microservice.bankcustomers.services.impl;

import java.util.Date;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;



import com.nttdata.microservice.bankcustomers.collections.PersonCollection;
import com.nttdata.microservice.bankcustomers.collections.enums.PersonStateEnum;
import com.nttdata.microservice.bankcustomers.collections.enums.PersonTypeEnum;
import com.nttdata.microservice.bankcustomers.controllers.PersonController;
import com.nttdata.microservice.bankcustomers.dto.PersonDto;
import com.nttdata.microservice.bankcustomers.exceptions.CustomerDuplicatedException;
import com.nttdata.microservice.bankcustomers.repository.IPersonRepository;
import com.nttdata.microservice.bankcustomers.services.IPersonService;
import com.nttdata.microservice.bankcustomers.utils.PersonUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PersonServiceImpl implements IPersonService{
	
	@Autowired
	private IPersonRepository repository;
	
	@Autowired
	private PersonUtils utils;
	
	@Override
	public Mono<PersonCollection> create(PersonDto personDto) {
		log.info("Start of operation to create a customer");

        log.info("Validating customer uniqueness");
        
        PersonCollection transformedCustomer = utils.toPersonCollection(personDto);
        Mono<PersonCollection> createdCustomer = repository.findAll()
                .filter(retrievedCustomer -> utils.checkDuplicatePerson(transformedCustomer, retrievedCustomer))
                .hasElements().flatMap(isARepeatedCustomer -> {
                    if (Boolean.TRUE.equals(isARepeatedCustomer)) {
                        log.warn("Customer does not accomplish with uniqueness specifications");
                        log.warn("Proceeding to abort create operation");
                        return Mono.error(new CustomerDuplicatedException("Customer does not accomplish with uniqueness specifications"));
                    }
                    else {
                        transformedCustomer.setState(PersonStateEnum.ACTIVE.toString());
                        log.info("Creating new customer: [{}]", transformedCustomer.toString());
                        Mono<PersonCollection> newCustomer = repository.save(transformedCustomer);
                        log.info("New customer was created successfully");

                        return newCustomer;
                    }
                });

        log.info("End of operation to create a customer");
        return createdCustomer;
	}
	
	@Override
	public Flux<PersonDto> list() {
		return repository.findAll()
				.map(collection -> {
					return utils.toPersonDto(collection);
				});
	}

	/*@Override
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
	}*/

	/*@Override
	public Mono<String> findByCode(String code) {
		return repository.findByCode(code).next().map(x->x.getCode());
	}*/

	/*@Cacheable(value = "personCache")
	@Override
	public Mono<String> findByCodeCached(String code) {
		return repository.findByCode(code).next().map(x->x.getCode()).cache();
	}*/

	@Override
	public Mono<PersonCollection> updateComment(String personCode, String comment) {
		return repository.findByCode(personCode).next().flatMap(collection -> {
			collection.setComment(comment);
			return repository.save(collection);
		});
	}


	@Override
	public Mono<PersonDto> findById(String id) {
		return repository.findById(new ObjectId(id)).flatMap(collection -> {
			return Mono.just(utils.toPersonDto(collection));
		});
	}
	
	@Cacheable(value = "personCache")
	@Override
	public Mono<PersonDto> findByIdCached(String id) {
		return repository.findById(new ObjectId(id)).flatMap(collection -> {
			return Mono.just(utils.toPersonDto(collection));
		}).cache();
	}
	
}
