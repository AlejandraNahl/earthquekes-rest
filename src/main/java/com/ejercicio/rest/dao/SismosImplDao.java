package com.ejercicio.rest.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ejercicio.rest.entity.Sismos;

@Repository
public class SismosImplDao implements ISismosDao{
	private static final Logger log = LoggerFactory.getLogger("com.ejercicio.rest.dao.ISismosImplDao");

	@Autowired
	private EntityManager entityManager;

	@Override
	public String save(Sismos sismo) {
		log.info("Ingreso al Metodo String save(Sismos sismo)");

		String respuesta = "OK";
		
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(sismo);
		return respuesta;
	}
	


}
