package com.nttdata.microservice.bankcustomers.collections;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "persons")
public class PersonCollection implements Serializable {

	@Id
	private ObjectId id;
	
	private String code;
	private String personType;
	
	//ENTERPRISE
	private String ruc;
	private String companyName;
	
	//PERSONAL
	private String firstName;
	private String lastName;
	private String typeDocument;
	private String numberDocument;
	private String comment;
	
	private String state;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	
}
