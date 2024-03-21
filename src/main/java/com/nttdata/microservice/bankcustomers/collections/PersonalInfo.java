package com.nttdata.microservice.bankcustomers.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonalInfo {
	
	private String firstName;
	private String lastName;
	private String typeDocument;
	private String numberDocument;

}
