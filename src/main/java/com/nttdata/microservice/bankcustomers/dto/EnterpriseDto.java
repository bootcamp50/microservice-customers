package com.nttdata.microservice.bankcustomers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseDto {
	private String ruc;
	private String companyName;
}