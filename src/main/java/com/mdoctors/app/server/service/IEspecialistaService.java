package com.mdoctors.app.server.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mdoctors.app.server.entity.Especialistas;

public interface IEspecialistaService {
	public List<Especialistas> findAll();
	public Page<Especialistas>findAll(Pageable pageable);
	public Especialistas findById(Long id);
	public Especialistas save(Especialistas especialista);
	public void delete(Long id);
	

}
