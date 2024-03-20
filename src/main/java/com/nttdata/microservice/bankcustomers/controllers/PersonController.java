package com.nttdata.microservice.bankcustomers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.microservice.bankcustomers.collections.PersonCollection;
import com.nttdata.microservice.bankcustomers.dto.EnterpriseDto;
import com.nttdata.microservice.bankcustomers.dto.PersonalDto;
import com.nttdata.microservice.bankcustomers.services.IPersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/person")
public class PersonController {
	
	@Autowired
	private IPersonService personService;

	@GetMapping(value = "/getCustomers")
	public Flux<PersonCollection> getCustomers() throws Exception{
		return personService.list();
	}
	
	@PostMapping(value = "/saveCustomerPersonal")
	public Mono<PersonCollection> saveCustomerPersonal(@RequestBody PersonalDto dto) throws Exception{
		PersonCollection collection = new PersonCollection();
		collection.setFirstName(dto.getFirstName());
		collection.setLastName(dto.getLastName());
		collection.setNumberDocument(dto.getNumberDocument());
		collection.setTypeDocument(dto.getTypeDocument());
		return personService.saveCustomerPersonal(collection);
	}
	
	@PostMapping(value = "/saveCustomerEnterprise")
	public Mono<PersonCollection> saveCustomerEnterprise(@RequestBody EnterpriseDto dto) throws Exception{
		PersonCollection collection = new PersonCollection();
		collection.setCompanyName(dto.getCompanyName());
		collection.setRuc(dto.getRuc());
		return personService.saveCustomerEnterprise(collection);
	}
	
	@GetMapping("/checkIfCustomerExist/{code}")
	public Mono<Boolean> checkIfCustomerExist(@PathVariable("code") String code)
			throws Exception {
		return personService.checkIfCustomerExist(code);
	}
	
	@GetMapping("/checkIfCustomerPersonal/{code}")
	public Mono<Boolean> checkIfCustomerPersonal(@PathVariable("code") String code)
			throws Exception {
		return personService.checkIfCustomerPersonal(code);
	}
	
	@GetMapping("/checkIfCustomerEnterprise/{code}")
	public Mono<Boolean> checkIfCustomerEnterprise(@PathVariable("code") String code)
			throws Exception {
		return personService.checkIfCustomerEnterprise(code);
	}
	
	@GetMapping("/getByCode/{code}")
	public Mono<String> getByCode(@PathVariable("code") String code)
			throws Exception {
		return personService.findByCode(code);
	}
	
	@GetMapping("/getByCodeCached/{code}")
	public Mono<String> getByCodeCached(@PathVariable("code") String code)
			throws Exception {
		return personService.findByCodeCached(code);
	}
	
	
}
