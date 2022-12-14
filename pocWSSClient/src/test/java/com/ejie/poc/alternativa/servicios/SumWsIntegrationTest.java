package com.ejie.poc.alternativa.servicios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ejie.poc.alternativa.servicios.SumRequest;
import com.ejie.poc.alternativa.servicios.SumResponse;
import com.ejie.poc.alternativa.servicios.SumWs;
import com.ejie.poc.alternativa.servicios.configuration.SumWSConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SumWSConfiguration.class)
@SpringBootTest
public class SumWsIntegrationTest {
	@Autowired
	@Qualifier("sumWSClientTest")
	private SumWs sumWSService;

	@Test
	public void shouldResultOK() {
		SumRequest request = new SumRequest();
		request.setNum1(10);
		request.setNum2(20);
		SumResponse response = sumWSService.calculateSum(request);
		assertNotNull(response);
		assertEquals(30, response.getResult());
	}
}

