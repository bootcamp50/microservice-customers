package com.nttdata.microservice.bankcustomers.collections;

import java.util.ArrayList;
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
public class EnterpriseInfo {
	private String ruc;
	private String companyName;
	private ArrayList<PersonalInfo> representatives;
}
