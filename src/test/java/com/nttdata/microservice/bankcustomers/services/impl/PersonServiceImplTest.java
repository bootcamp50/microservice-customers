package com.nttdata.microservice.bankcustomers.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nttdata.microservice.bankcustomers.collections.PersonCollection;
import com.nttdata.microservice.bankcustomers.collections.enums.PersonTypeEnum;
import com.nttdata.microservice.bankcustomers.repository.IPersonRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class PersonServiceImplTest {
	
	@InjectMocks
	PersonServiceImpl service;
	
	@Mock
	IPersonRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Test save customer personal")
	void saveCustomerPersonalTest() {
		
		PersonCollection person = new PersonCollection();
		person.setFirstName("Nombre");
		person.setLastName("Apellido");
		person.setNumberDocument("123456");
		Mono<PersonCollection> personMono = Mono.just(person);
		
		when(repository.save(person)).thenReturn(personMono);

		Mono<PersonCollection> expected = service.saveCustomerPersonal(person);
		assertNotNull(expected);
	}

	@Test
	@DisplayName("Test save customer enterprise")
	void saveCustomerEnterpriseTest() {
		
		PersonCollection person = new PersonCollection();
		person.setCompanyName("Company");
		person.setRuc("123879");
		Mono<PersonCollection> personMono = Mono.just(person);
		
		when(repository.save(person)).thenReturn(personMono);

		Mono<PersonCollection> expected = service.saveCustomerEnterprise(person);
		assertNotNull(expected);
		
	}

	

	@Test
	@DisplayName("Test check if customer exist ")
	void checkIfCustomerExistTest() {
		
		PersonCollection person = new PersonCollection();
		person.setFirstName("Nombre");
		person.setCode("1111");
		person.setLastName("Apellido");
		person.setNumberDocument("123456");
		Flux<PersonCollection> personFlux = Flux.just(person);
		
		when(repository.findByCode("1111")).thenReturn(personFlux);
		
		Mono<Boolean> expected = service.checkIfCustomerExist("1111");
		assertNotNull(expected);
		assertEquals(true,expected.block());
		
		
	}

	@Test
	@DisplayName("Test check if customer personal exist ")
	void checkIfCustomerPersonalTest() {
		
		PersonCollection person = new PersonCollection();
		person.setFirstName("Nombre");
		person.setPersonType(PersonTypeEnum.PERSONAL.toString());
		person.setCode("1111");
		person.setLastName("Apellido");
		person.setNumberDocument("123456");
		Flux<PersonCollection> personFlux = Flux.just(person);
		
		when(repository.findByCode("1111")).thenReturn(personFlux);
		
		Mono<Boolean> expected = service.checkIfCustomerPersonal("1111");
		assertNotNull(expected);
		assertEquals(true,expected.block());
	}

	@Test
	@DisplayName("Test check if customer personal exist ")
	void checkIfCustomerEnterpriseTest() {
		
		PersonCollection person = new PersonCollection();
		person.setFirstName("Nombre");
		person.setPersonType(PersonTypeEnum.ENTERPRISE.toString());
		person.setCode("1111");
		person.setLastName("Apellido");
		person.setNumberDocument("123456");
		Flux<PersonCollection> personFlux = Flux.just(person);
		
		when(repository.findByCode("1111")).thenReturn(personFlux);
		
		Mono<Boolean> expected = service.checkIfCustomerEnterprise("1111");
		assertNotNull(expected);
		assertEquals(true,expected.block());
		
	}
	
	@Test
	@DisplayName("Test list all customers")
	void listTest() {
		
		PersonCollection person = new PersonCollection();
		person.setFirstName("Nombre");
		person.setPersonType(PersonTypeEnum.ENTERPRISE.toString());
		person.setCode("1111");
		person.setLastName("Apellido");
		person.setNumberDocument("123456");
		
		PersonCollection person2 = new PersonCollection();
		person2.setFirstName("Nombre2");
		person2.setPersonType(PersonTypeEnum.ENTERPRISE.toString());
		person2.setCode("2222");
		person2.setLastName("Apellido2");
		person2.setNumberDocument("123789");
		Flux<PersonCollection> personFlux = Flux.just(person, person2);
		
		when(repository.findAll()).thenReturn(personFlux);
		
		Flux<PersonCollection> expected = service.list();
		assertNotNull(expected);
	}
	

}
