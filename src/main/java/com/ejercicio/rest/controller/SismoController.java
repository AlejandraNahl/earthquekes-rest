package com.ejercicio.rest.controller;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ejercicio.rest.dto.SismosPorFechaDto;
import com.ejercicio.rest.dto.SismosPorMagnitudDto;
import com.ejercicio.rest.entity.Sismos;
import com.ejercicio.rest.service.ISismosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("sismos")
@Api(tags = "sismos")
public class SismoController {

	@Autowired
	private ISismosService iSismosService;

	/**
	 * Endpoint para obtener los sismos a partir de una fecha inicial y una fecha final
	 * 
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/buscarPorFecha", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Sismos por Fecha", notes = "Sismos a partir de una fechaInicial y fechaFinal")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "El servidor no puede procesar el request") })
	public ResponseEntity<?> buscarPorFecha(@RequestBody SismosPorFechaDto sismosPorFecha) {

		RestTemplate restTemplate = new RestTemplate();
		String json;

		String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime="
				+ sismosPorFecha.getStarttime() + "&endtime=" + sismosPorFecha.getEndtime();

		try {
			json = restTemplate.getForEntity(url, String.class).getBody();
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<>(sismosPorFecha, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	/**
	 * Endpoint para obtener los sismos a partir magnitud inicial y una magnitud final
	 * 
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/buscarPorMagnitud", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Sismos por Magnitud", notes = "Sismos a partir de una maxMagnitude y minMagnitud")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "El servidor no puede procesar el request") })
	public ResponseEntity<?> buscarPorMagnitud(@RequestBody SismosPorMagnitudDto sismosPorMagnitud) {

		RestTemplate restTemplate = new RestTemplate();
		String json;

		String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude="
				+ sismosPorMagnitud.getMinMagnitude() + "&maxmagnitude=" + sismosPorMagnitud.getMaxMagnitude();

		try {
			json = restTemplate.getForEntity(url, String.class).getBody();
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<>(sismosPorMagnitud, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	/**
	 * Endpoint para almacenar en una base de datos H2 todos los sismos que hayan
	 * ocurrido durante el día en que se hace el llamado al endpoint
	 * 
	 * @throws org.apache.tomcat.util.json.ParseException
	 */
	@RequestMapping(value = "/guardarH2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Almacenar los Sismos del dia", notes = "Guardar en H2 los sismos identificados")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK") })
	public void guardarH2() throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String fechaActual = formatter.format(date);

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		String fechaActual1 = formatter.format(cal.getTime());

		String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" + fechaActual
				+ "&endtime=" + fechaActual1;
		RestTemplate restTemplate = new RestTemplate();
		String json = restTemplate.getForEntity(url, String.class).getBody();

		JSONParser jParser = new JSONParser(json);
		LinkedHashMap<?, ?> linkedHashMap = (LinkedHashMap<?, ?>) jParser.parse();
		List<?> lista = (List<?>) linkedHashMap.get("features");

		for (Object o : lista) {
			LinkedHashMap<?, ?> feature = (LinkedHashMap<?, ?>) o;
			LinkedHashMap<?, ?> maps = (LinkedHashMap<?, ?>) feature.get("properties");

			String magnitud = maps.get("mag").toString();
			String fecha = maps.get("time").toString();

			LocalDate fechaFormato = Instant.ofEpochMilli(Long.parseLong(fecha)).atZone(ZoneId.systemDefault()).toLocalDate();
			
			Sismos sismos = new Sismos();
			sismos.setFecha(fechaFormato.toString());
			sismos.setMagnitud(magnitud);
			
			iSismosService.save(sismos);
		}
	}

}
