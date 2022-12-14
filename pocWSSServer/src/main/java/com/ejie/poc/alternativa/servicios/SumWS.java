package com.ejie.poc.alternativa.servicios;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.ejie.poc.alternativa.servicios.dto.SumRequest;
import com.ejie.poc.alternativa.servicios.dto.SumResponse;

@WebService(name = "SumWs")
public interface SumWS {
	@WebResult(name = "response")
	SumResponse calculateSum(@WebParam SumRequest request);
}
