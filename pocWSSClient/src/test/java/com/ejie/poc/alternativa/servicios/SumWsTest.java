package com.ejie.poc.alternativa.servicios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.junit.Test;

import com.ejie.poc.alternativa.servicios.PasswordCallbackHandler;
import com.ejie.poc.alternativa.servicios.SumRequest;
import com.ejie.poc.alternativa.servicios.SumResponse;
import com.ejie.poc.alternativa.servicios.SumWSService;
import com.ejie.poc.alternativa.servicios.SumWs;

public class SumWsTest {

	@Test
	public void calculateSumShouldReturnAValidResult() {
		try {
			SumWSService service = new SumWSService(new URL("http://localhost:8080/sumapp/services/sumService?wsdl"));
			SumWs port = service.getSumWsPort();
			Client client = ClientProxy.getClient(port);
			Endpoint endpoint = client.getEndpoint();

			HashMap<String, Object> outProps = new HashMap<String, Object>();
			outProps.put(WSHandlerConstants.ACTION, "UsernameToken Timestamp");
//			outProps.put(WSHandlerConstants.ACTION, "UsernameToken Timestamp Signature Encrypt");
			outProps.put(WSHandlerConstants.USER, "sumuser");
			outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
			outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, PasswordCallbackHandler.class.getName());

//			outProps.put(WSHandlerConstants.ENCRYPTION_USER, "myserverkey");
//			outProps.put(WSHandlerConstants.ENC_PROP_FILE, "etc/clientKeyStore.properties");
//			outProps.put(WSHandlerConstants.ENCRYPTION_PARTS,
//					"{Element}{http://www.w3.org/2000/09/xmldsig#}Signature;{Content}{http://schemas.xmlsoap.org/soap/envelope/}Body");

//			outProps.put(WSHandlerConstants.SIGNATURE_USER, "myclientkey");
//			outProps.put(WSHandlerConstants.SIG_PROP_FILE, "etc/clientKeyStore.properties");
//			outProps.put(
//					WSHandlerConstants.SIGNATURE_PARTS,
//					"{Element}{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp;{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body");

			outProps.put("timeToLive", "30");

			WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
			endpoint.getOutInterceptors().add(wssOut);

			HashMap<String, Object> inProps = new HashMap<>();
			inProps.put(WSHandlerConstants.ACTION, "Timestamp");
//			inProps.put(WSHandlerConstants.ACTION, "Encrypt Signature Timestamp");
			
//			inProps.put(WSHandlerConstants.DEC_PROP_FILE, "etc/clientKeyStore.properties");
//			inProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, PasswordCallbackHandler.class.getName());
//			inProps.put(WSHandlerConstants.SIG_PROP_FILE, "etc/clientKeyStore.properties");

			WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps);
			endpoint.getInInterceptors().add(wssIn);

			SumRequest request = new SumRequest();
			request.setNum1(10);
			request.setNum2(20);
			SumResponse response = port.calculateSum(request);
			assertNotNull(response);
			assertEquals(30, response.getResult());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
}
