package com.ejie.poc.alternativa.servicios;

import com.ejie.poc.alternativa.servicios.dto.SumRequest;
import com.ejie.poc.alternativa.servicios.dto.SumResponse;

public class SumWSImpl implements SumWS {

	@Override
	public SumResponse calculateSum(SumRequest request) {
		int result = request.getNum1() + request.getNum2();
		SumResponse response = new SumResponse();
		response.setResult(result);
		return response;
	}

}
