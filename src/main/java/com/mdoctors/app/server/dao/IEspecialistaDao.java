package com.mdoctors.app.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdoctors.app.server.entity.Especialistas;

public interface IEspecialistaDao extends JpaRepository<Especialistas,Long>{

}
