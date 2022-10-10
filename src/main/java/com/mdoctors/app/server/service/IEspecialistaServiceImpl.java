package com.mdoctors.app.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdoctors.app.server.dao.IEspecialistaDao;
import com.mdoctors.app.server.entity.Especialistas;
@Service
public class IEspecialistaServiceImpl implements IEspecialistaService{
	
	@Autowired
	IEspecialistaDao especialistaDao;

	@Transactional(readOnly=true)
	public List<Especialistas> findAll() {
		
		return especialistaDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Especialistas> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return especialistaDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Especialistas findById(Long id) {
		// TODO Auto-generated method stub
		return especialistaDao.findById(id).orElse(null);
	}

	@Override
	public Especialistas save(Especialistas especialista) {
		// TODO Auto-generated method stub
		return especialistaDao.save(especialista);
	}

	@Override
	@Transactional(readOnly=true)
	public void delete(Long id) {
		especialistaDao.deleteById(id);
		
	}

}
