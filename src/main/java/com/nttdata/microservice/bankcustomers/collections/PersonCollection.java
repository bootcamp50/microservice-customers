package com.nttdata.microservice.bankcustomers.collections;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "persons")
public class PersonCollection {

	@Id
	private ObjectId id;
	
	private String code;
	private String personType;
	
	//ENTERPRISE
	private EnterpriseInfo enterpriseInfo; 
	
	//PERSONAL
	private PersonalInfo personalInfo;

	private String comment;
	
	private String state;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	
}
