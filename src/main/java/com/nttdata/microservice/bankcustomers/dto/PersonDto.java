package com.nttdata.microservice.bankcustomers.dto;

import com.nttdata.microservice.bankcustomers.collections.EnterpriseInfo;
import com.nttdata.microservice.bankcustomers.collections.PersonalInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
	
	private String id;
	private String personType;
	private String state;
	private PersonalInfo personalInfo;
	private EnterpriseInfo enterpriseInfo;

}
