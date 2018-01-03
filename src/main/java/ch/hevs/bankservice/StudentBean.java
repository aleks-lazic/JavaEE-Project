package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.hevs.businessobject.Student;

@Stateless
public class StudentBean implements IStudent {

	@PersistenceContext(name = "schoolPU")
	private EntityManager em;

	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return (List<Student>) em.createQuery("FROM " + Student.class.getName()).getResultList();
	}

	@Override
	public Student getStudentById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
