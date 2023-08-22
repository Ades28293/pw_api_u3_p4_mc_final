package com.example.demo.repository;

import java.util.List;

import com.example.demo.repository.modelo.Estudiante;

public interface IEstudianteRepository {
	
	public Estudiante seleccionarPorCedula(String cedula);
	
	public Estudiante seleccionarPorId(Integer id);
	
	public void insertar(Estudiante estudiante);
	
	public void actualizar(Estudiante estudiante);
	
	public void actualizarParcial(String cedulaActual, String cedulaNueva);
	
	public void borrar(Integer id);
	
	public List<Estudiante> seleccionarTodo(String provincia);
	
	public List<Estudiante> buscarTodos();

}
