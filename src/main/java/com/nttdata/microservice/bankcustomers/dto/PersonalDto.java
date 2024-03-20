package com.nttdata.microservice.bankcustomers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalDto {
	private String firstName;
	private String lastName;
	private String typeDocument;
	private String numberDocument;
}
