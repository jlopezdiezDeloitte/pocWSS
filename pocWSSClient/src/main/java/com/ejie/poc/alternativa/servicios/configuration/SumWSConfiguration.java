package com.ejie.poc.alternativa.servicios.configuration;

import static org.apache.cxf.Bus.DEFAULT_BUS_ID;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.ws.Endpoint;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.ejie.poc.alternativa.servicios.PasswordCallbackHandler;
import com.ejie.poc.alternativa.servicios.SumWs;

@Configuration
@PropertySource("classpath:application-${spring.profiles.active}.properties")
public class SumWSConfiguration {
	private static final Logger log = LoggerFactory.getLogger(SumWSConfiguration.class.getName());
	// aplication.yml
	@Value("${service.url}")
	private String serviceUrl;
	
	/**
	 * Ubicación del wsdl y el endpoint
	 */
	@Bean(name = "sumWSClientTest")
	public SumWs serviceTest() {
		JaxWsProxyFactoryBean jaxWsProxyFactory = new JaxWsProxyFactoryBean();
		jaxWsProxyFactory.setServiceClass(SumWs.class);
		jaxWsProxyFactory.setAddress(serviceUrl);
		log.info("Consumiendo servicio de " + serviceUrl);
		jaxWsProxyFactory.getOutInterceptors().add(wss4jOut());
		jaxWsProxyFactory.getInInterceptors().add(wss4jIn());
		return (SumWs) jaxWsProxyFactory.create();
	}

	public WSS4JOutInterceptor wss4jOut() {
		HashMap<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION, "UsernameToken Timestamp Signature Encrypt");
		
		outProps.put(WSHandlerConstants.USER, "sumuser");
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, PasswordCallbackHandler.class.getName());

		outProps.put(WSHandlerConstants.ENCRYPTION_USER, "myserverkey");
		outProps.put(WSHandlerConstants.ENC_PROP_FILE, "clientKeystore.properties");
		outProps.put(WSHandlerConstants.ENCRYPTION_PARTS,
				"{Element}{http://www.w3.org/2000/09/xmldsig#}Signature;{Content}{http://schemas.xmlsoap.org/soap/envelope/}Body");

		outProps.put(WSHandlerConstants.SIGNATURE_USER, "myclientkey");
		outProps.put(WSHandlerConstants.SIG_PROP_FILE, "clientKeystore.properties");
		outProps.put(
				WSHandlerConstants.SIGNATURE_PARTS,
				"{Element}{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp;{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body");

		outProps.put("timeToLive", "30");
		
		WSS4JOutInterceptor interceptor = new WSS4JOutInterceptor(outProps);
		return interceptor;
	}
	
	public WSS4JInInterceptor wss4jIn() {
		HashMap<String, Object> inProps = new HashMap<>();
		inProps.put(WSHandlerConstants.ACTION, "Encrypt Signature Timestamp");
		
		inProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, PasswordCallbackHandler.class.getName());
		
		inProps.put(WSHandlerConstants.DEC_PROP_FILE, "clientKeyStore.properties");

		inProps.put(WSHandlerConstants.SIG_PROP_FILE, "clientKeystore.properties");

		WSS4JInInterceptor interceptor = new WSS4JInInterceptor(inProps);
		return interceptor;
	}

}