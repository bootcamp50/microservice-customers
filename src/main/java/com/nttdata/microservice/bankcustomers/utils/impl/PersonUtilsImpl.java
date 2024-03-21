package com.nttdata.microservice.bankcustomers.utils.impl;

import org.springframework.stereotype.Component;

import com.nttdata.microservice.bankcustomers.collections.PersonCollection;
import com.nttdata.microservice.bankcustomers.collections.enums.PersonTypeEnum;
import com.nttdata.microservice.bankcustomers.dto.PersonDto;
import com.nttdata.microservice.bankcustomers.services.impl.PersonServiceImpl;
import com.nttdata.microservice.bankcustomers.utils.PersonUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PersonUtilsImpl implements PersonUtils{

	@Override
	public PersonCollection toPersonCollection(PersonDto personDto) {
		return PersonCollection.builder()
                .personalInfo(personDto.getPersonalInfo())
                .enterpriseInfo(personDto.getEnterpriseInfo())
                .personType(personDto.getPersonType())
                .build();
	}
	
	@Override
	public PersonDto toPersonDto(PersonCollection personCollection) {
		return PersonDto.builder()
				.id(String.valueOf(personCollection.getId()))
				.personType(personCollection.getPersonType())
				.state(personCollection.getState())
				.personalInfo(personCollection.getPersonalInfo())
				.enterpriseInfo(personCollection.getEnterpriseInfo())
				.build();
	}

	@Override
	public Boolean checkDuplicatePerson(PersonCollection personA, PersonCollection personB) {
		
		log.info("Creating personA: [{}]",personA.toString());
		log.info("Creating personB: [{}]",personB.toString());
		 // A customer can not be duplicated by its own
        if (personA.getId() != null &&
        		personB.getId() != null &&
        				String.valueOf(personA.getId()).contentEquals(String.valueOf(personB.getId()))) return false;

        // If it's evaluating different customer groups (personal and business) then unique values do not repeat each other
        if (!personA.getPersonType().contentEquals(personB.getPersonType())) return false;

        if (personA.getPersonType().contentEquals(PersonTypeEnum.PERSONAL.toString())
        		|| personA.getPersonType().contentEquals(PersonTypeEnum.PERSONAL_VIP.toString())) {
        	
            return personA.getPersonalInfo().getNumberDocument().contentEquals(personB.getPersonalInfo().getNumberDocument());

        } else if (personA.getPersonType().contentEquals(PersonTypeEnum.ENTERPRISE.toString())
        		|| personA.getPersonType().contentEquals(PersonTypeEnum.ENTERPRISE_PYME.toString())) {
        	
            return personA.getEnterpriseInfo().getRuc().contentEquals(personB.getEnterpriseInfo().getRuc()) ||
            		personA.getEnterpriseInfo().getCompanyName().contentEquals(personB.getEnterpriseInfo().getCompanyName());

        } else return false;
	}

	

}
