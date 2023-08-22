package com.example.demo.repository;

import java.util.List;

import com.example.demo.repository.modelo.Materia;

public interface IMateriaRepository {

	public List<Materia> buscarPorCedulaEstudiante(String cedula);
	
	public Materia buscarPorId(Integer id);
	
}
