package com.ejercicio.rest;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ejercicio.rest.controller.SismoController;
import com.ejercicio.rest.service.SismosServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SismoController.class)
public class SismoControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SismosServiceImpl sismosService;

	@Test
	public void testGuardarSismos() throws Exception {
		String exampleSismosJson = "{\"starttime\":\"2019-01-01\",\"endtime\":\"2019-01-31\"}";

		String uri = "/sismos/buscarPorFecha";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(exampleSismosJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println("response" + response.getStatus());

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		// assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}
}
