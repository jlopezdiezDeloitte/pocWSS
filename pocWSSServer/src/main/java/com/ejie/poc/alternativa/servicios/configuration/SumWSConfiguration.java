package com.ejie.poc.alternativa.servicios.configuration;

import static org.apache.cxf.Bus.DEFAULT_BUS_ID;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Endpoint;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
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
import com.ejie.poc.alternativa.servicios.SumWSImpl;

@Configuration
@PropertySource("application-${spring.profiles.active}.properties")
public class SumWSConfiguration {
	private static final Logger log = LoggerFactory.getLogger(SumWSConfiguration.class.getName());
	// aplication.yml
	@Value("${service.contextPath}")
	private String contextPath;
	@Value("${service.endpointUrl}")
	private String endpointUrl;
	@Value("${service.wsdlLocation}")
	private String wsdlLocation;
	
	/**
	 * Contexto del servicio
	 */
	@Bean
	public ServletRegistrationBean dispatcherServlet() {
		return new ServletRegistrationBean(new CXFServlet(), contextPath);
	}
	/**
	 * Bus de integración CXF/Spring
	 */
	@Bean(name = DEFAULT_BUS_ID)
	public SpringBus springBus() {
		SpringBus springBus = new SpringBus();
		springBus.setFeatures(Arrays.asList(new LoggingFeature()));
		return springBus;
	}
	/**
	 * Implementación del servicio WEB
	 */
	public SumWSImpl demoServiceEndpoint() {
		return new SumWSImpl();
	}
	/**
	 * Ubicación del wsdl y el endpoint
	 */
	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(springBus(), demoServiceEndpoint());
		endpoint.publish(endpointUrl);
		log.info("Publicando servicio en " + endpointUrl);
		endpoint.setWsdlLocation(wsdlLocation);
		endpoint.getInInterceptors().add(wss4jIn());
		endpoint.getOutInterceptors().add(wss4jOut());
		return endpoint;
	}

	public WSS4JInInterceptor wss4jIn() {
		Map<String, Object> inProps = new HashMap<>();
		inProps.put(ConfigurationConstants.ACTION, "UsernameToken Timestamp Signature Encrypt");
		inProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		inProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, PasswordCallbackHandler.class.getName());

		inProps.put(WSHandlerConstants.DEC_PROP_FILE, "serverKeystore.properties");
		
		inProps.put(WSHandlerConstants.SIG_PROP_FILE, "serverKeystore.properties");

		WSS4JInInterceptor interceptor = new WSS4JInInterceptor(inProps);
		return interceptor;
	}
	
	public WSS4JOutInterceptor wss4jOut() {
		Map<String, Object> outProps = new HashMap<>();
		outProps.put(ConfigurationConstants.ACTION, "Timestamp Signature Encrypt");
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, PasswordCallbackHandler.class.getName());

		outProps.put(WSHandlerConstants.ENCRYPTION_USER, "myclientkey");
		outProps.put(WSHandlerConstants.ENC_PROP_FILE, "serverKeystore.properties");
		outProps.put(
				WSHandlerConstants.ENCRYPTION_PARTS,
				"{Element}{http://www.w3.org/2000/09/xmldsig#}Signature;{Content}{http://schemas.xmlsoap.org/soap/envelope/}Body");
		
		outProps.put(WSHandlerConstants.SIGNATURE_USER, "myserverkey");
		outProps.put(WSHandlerConstants.SIG_PROP_FILE, "serverKeystore.properties");
		outProps.put(
				WSHandlerConstants.SIGNATURE_PARTS,
				"{Element}{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}" + 
				"Timestamp;" + 
				"{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body");
		WSS4JOutInterceptor interceptor = new WSS4JOutInterceptor(outProps);
		return interceptor;
	}

}