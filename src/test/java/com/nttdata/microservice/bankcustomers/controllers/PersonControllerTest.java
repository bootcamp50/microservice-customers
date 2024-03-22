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
import com.nttdata.microservice.bankcustomers.collections.enums.PersonStateEnum;
import com.nttdata.microservice.bankcustomers.collections.enums.PersonTypeEnum;
import com.nttdata.microservice.bankcustomers.dto.PersonDto;
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
		PersonDto person = new PersonDto();
		person.setPersonType(PersonTypeEnum.PERSONAL.toString());
		person.setState(PersonStateEnum.ACTIVE.toString());
		
		PersonDto person2 = new PersonDto();
		person2.setPersonType(PersonTypeEnum.ENTERPRISE.toString());
		person2.setState(PersonStateEnum.ACTIVE.toString());
		
		Flux<PersonDto> personFlux = Flux.just(person,person2);
		
		when(service.list()).thenReturn(personFlux);
		
		Flux<PersonDto> response = webTestClient.get().uri("/person/getCustomers")
				.exchange()
				.expectStatus().isOk()
				.returnResult(PersonDto.class)
				.getResponseBody();
				
				StepVerifier.create(response)
				.expectSubscription()
				.expectNext(person)
				.expectNext(person2)
				.verifyComplete();
				
	}
	
	/*@Test
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
		
		
	}*/

	

	/*@Test
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
	}*/
	

}
