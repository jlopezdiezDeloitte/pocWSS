package com.servicios.alternativa.poc.ejie;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.4.5
 * 2022-12-14T16:24:52.308+01:00
 * Generated source version: 3.4.5
 *
 */
@WebService(targetNamespace = "http://servicios.alternativa.poc.ejie.com/", name = "SumWs")
@XmlSeeAlso({ObjectFactory.class})
public interface SumWs {

    @WebMethod
    @RequestWrapper(localName = "calculateSum", targetNamespace = "http://servicios.alternativa.poc.ejie.com/", className = "com.servicios.alternativa.poc.ejie.CalculateSum")
    @ResponseWrapper(localName = "calculateSumResponse", targetNamespace = "http://servicios.alternativa.poc.ejie.com/", className = "com.servicios.alternativa.poc.ejie.CalculateSumResponse")
    @WebResult(name = "response", targetNamespace = "")
    public com.servicios.alternativa.poc.ejie.SumResponse calculateSum(

        @WebParam(name = "arg0", targetNamespace = "")
        com.servicios.alternativa.poc.ejie.SumRequest arg0
    );
}
