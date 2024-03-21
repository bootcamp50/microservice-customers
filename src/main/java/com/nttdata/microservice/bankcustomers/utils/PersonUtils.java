package com.nttdata.microservice.bankcustomers.utils;

import com.nttdata.microservice.bankcustomers.collections.PersonCollection;
import com.nttdata.microservice.bankcustomers.dto.PersonDto;

public interface PersonUtils {
	
	PersonCollection toPersonCollection(PersonDto personDto);
	PersonDto toPersonDto(PersonCollection personCollection);
	Boolean checkDuplicatePerson(PersonCollection personA, PersonCollection personB);

}
