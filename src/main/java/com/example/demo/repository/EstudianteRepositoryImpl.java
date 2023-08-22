package com.example.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.modelo.Estudiante;

@Repository
@Transactional
public class EstudianteRepositoryImpl implements IEstudianteRepository{
	
	@PersistenceContext
	private EntityManager entityManeger;

	@Override
	public Estudiante seleccionarPorCedula(String cedula) {
		// TODO Auto-generated method stub
		TypedQuery<Estudiante> myQuery = this.entityManeger.createQuery("SELECT e FROM Estudiante e WHERE e.cedula=:datoCedula", Estudiante.class);
		myQuery.setParameter("datoCedula", cedula);
		return myQuery.getSingleResult();
	}

	@Override
	public void insertar(Estudiante estudiante) {
		// TODO Auto-generated method stub
		this.entityManeger.persist(estudiante);
		
	}

	@Override
	public void actualizar(Estudiante estudiante) {
		// TODO Auto-generated method stub
		this.entityManeger.merge(estudiante);
		
	}

	@Override
	public void actualizarParcial(String cedulaActual, String cedulaNueva) {
		// TODO Auto-generated method stub
		Query myQuery= this.entityManeger
				.createQuery("UPDATE Estudiante e SET e.cedula=:datoCedula WHERE e.cedula=:datoCondicion");
		
		myQuery.setParameter("datoCedula", cedulaNueva);
		myQuery.setParameter("datodatoCondicion", cedulaActual);
		myQuery.executeUpdate();
		
		
	}

	@Override
	public void borrar(Integer id) {
		// TODO Auto-generated method stub
		
		this.entityManeger.remove(this.buscarPorId(id));
	}
	
	
	public Estudiante buscarPorId(Integer id) {
		return this.entityManeger.find(Estudiante.class, id);
	}

	@Override
	public List<Estudiante> seleccionarTodo(String provincia) {
		// TODO Auto-generated method stub
		TypedQuery<Estudiante> myQuery = this.entityManeger.createQuery("SELECT e FROM Estudiante e WHERE e.provincia=:datoProvincia", Estudiante.class);
		myQuery.setParameter("datoProvincia", provincia);
		return myQuery.getResultList();
		
	}

	@Override
	public List<Estudiante> buscarTodos() {
		// TODO Auto-generated method stub
		TypedQuery<Estudiante> myQuery = this.entityManeger.createQuery("SELECT e FROM Estudiante e", Estudiante.class);
		return myQuery.getResultList();
	}

	@Override
	public Estudiante seleccionarPorId(Integer id) {
		// TODO Auto-generated method stub
		return this.entityManeger.find(Estudiante.class, id);
	}

}
