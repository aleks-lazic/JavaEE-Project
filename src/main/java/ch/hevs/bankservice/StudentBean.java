package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	@Override
	public Student getStudentByFirstnameAndLastname(String firstname, String lastname) {
		// TODO Auto-generated method stub
		Query query = em.createQuery(
				"FROM " + Student.class.getName() + " s WHERE s.firstname = :firstname AND s.lastname = :lastname");
		query.setParameter("firstname", firstname);
		query.setParameter("lastname", lastname);
		return (Student) query.getSingleResult();
	}

	@Override
	public void changeClass(Long idStudent, Long idClass) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("UPDATE " + Student.class.getName() + " SET CLASSNAME_ID = " + idClass + " WHERE ID = " + idStudent);
		query.executeUpdate();
	}

}
