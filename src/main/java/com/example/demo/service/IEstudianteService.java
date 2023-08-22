package com.example.demo.service;

import java.util.List;

import com.example.demo.repository.modelo.Estudiante;
import com.example.demo.service.to.EstudianteTO;

public interface IEstudianteService {
	
	public Estudiante consultarPorCedula(String cedula);
	
	public void guardar(Estudiante estudiante);

	public void actualizar(Estudiante estudiante);
	
	public void actualizarParcial(String cedulaActual, String cedulaNueva);
	
	public void eliminar(Integer id);
	
	public List<Estudiante> buscarTodos(String provincia);
	
	public List<EstudianteTO> buscarTodos();
	
	public EstudianteTO consultarPorId(Integer id);
}
