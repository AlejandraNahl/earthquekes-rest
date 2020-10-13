package com.ejercicio.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejercicio.rest.dao.ISismosDao;
import com.ejercicio.rest.entity.Sismos;

@Service
public class SismosServiceImpl implements ISismosService{
	
	@Autowired
	private ISismosDao iSismosDao;
	

	@Override
	public String save(Sismos sismo) {
		return iSismosDao.save(sismo);
	}

}
