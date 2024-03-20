package com.nttdata.microservice.bankcustomers.controllers;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nttdata.microservice.bankcustomers.collections.PersonCollection;

import com.nttdata.microservice.bankcustomers.services.IPersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@RunWith(SpringRunner.class)
@WebFluxTest(PersonController.class)
class PersonControllerTest {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private IPersonService service;
	

	@BeforeEach
	void setUp() throws Exception {
	}

	
	@Test
	@DisplayName("Test list all customers")
	void getCustomers() {
		PersonCollection person = new PersonCollection();
		person.setFirstName("Nombre");
		person.setLastName("Apellido");
		person.setNumberDocument("123456");
		
		PersonCollection person2 = new PersonCollection();
		person2.setFirstName("Nombre2");
		person2.setLastName("Apellido2");
		person2.setNumberDocument("123457");
		
		Flux<PersonCollection> personFlux = Flux.just(person,person2);
		
		when(service.list()).thenReturn(personFlux);
		
		Flux<PersonCollection> response = webTestClient.get().uri("/person/getCustomers")
				.exchange()
				.expectStatus().isOk()
				.returnResult(PersonCollection.class)
				.getResponseBody();
				
				StepVerifier.create(response)
				.expectSubscription()
				.expectNext(person)
				.expectNext(person2)
				.verifyComplete();
				
	}
	
	@Test
	@DisplayName("Test save customer personal")
	void saveCustomerPersonalTest() {
		
		PersonCollection person = new PersonCollection();
		person.setFirstName("Nombre");
		person.setLastName("Apellido");
		person.setNumberDocument("123456");
		Mono<PersonCollection> personMono = Mono.just(person) ;
		
		when(service.saveCustomerPersonal(person)).thenReturn(Mono.just(person));
		
		webTestClient.post().uri("/person/saveCustomerPersonal")
		.body(Mono.just(personMono),PersonCollection.class)
		.exchange()
		.expectStatus().isOk();
	}

	@Test
	@DisplayName("Test save customer enterprise")
	void saveCustomerEnterpriseTest() {
		
		PersonCollection person = new PersonCollection();
		person.setCompanyName("Company");
		person.setRuc("123879");
		Mono<PersonCollection> personMono = Mono.just(person);
		
		when(service.saveCustomerEnterprise(person)).thenReturn(Mono.just(person));
		
		webTestClient.post().uri("/person/saveCustomerEnterprise")
		.body(Mono.just(personMono),PersonCollection.class)
		.exchange()
		.expectStatus().isOk();
		
		
	}

	

	@Test
	@DisplayName("Test check if customer exist ")
	void checkIfCustomerExistTest() {
		
		when(service.checkIfCustomerExist("123789")).thenReturn(Mono.just(true));

		Flux<Boolean> response = webTestClient.get().uri("/person/checkIfCustomerExist/123789")
		.exchange()
		.expectStatus().isOk()
		.returnResult(Boolean.class)
		.getResponseBody();
		
		StepVerifier.create(response)
		.expectSubscription()
		.expectNext(true)
		.verifyComplete();
		
		
	}

	@Test
	@DisplayName("Test check if customer personal exist ")
	void checkIfCustomerPersonalTest() {
		
		when(service.checkIfCustomerPersonal("123789")).thenReturn(Mono.just(true));

		Flux<Boolean> response = webTestClient.get().uri("/person/checkIfCustomerPersonal/123789")
		.exchange()
		.expectStatus().isOk()
		.returnResult(Boolean.class)
		.getResponseBody();
		
		StepVerifier.create(response)
		.expectSubscription()
		.expectNext(true)
		.verifyComplete();
		
	}

	@Test
	@DisplayName("Test check if customer enterprise exist ")
	void checkIfCustomerEnterpriseTest() {
		when(service.checkIfCustomerEnterprise("123789")).thenReturn(Mono.just(true));

		Flux<Boolean> response = webTestClient.get().uri("/person/checkIfCustomerEnterprise/123789")
		.exchange()
		.expectStatus().isOk()
		.returnResult(Boolean.class)
		.getResponseBody();
		
		StepVerifier.create(response)
		.expectSubscription()
		.expectNext(true)
		.verifyComplete();
	}
	

}
