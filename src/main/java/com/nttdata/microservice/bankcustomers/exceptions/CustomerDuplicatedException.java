package com.nttdata.microservice.bankcustomers.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerDuplicatedException extends IllegalArgumentException {
	private static final long serialVersionUID = -5713584292717311038L;

    public CustomerDuplicatedException(String s) {
        super(s);
    }
}
