package ch.hevs.bankservice;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ch.hevs.businessobject.ClassName;
import ch.hevs.businessobject.Mark;
import ch.hevs.businessobject.Student;
import ch.hevs.businessobject.Subject;

@Stateless
@RolesAllowed(value = {"administrator", "student"})
public class StudentBean implements IStudent {

	@PersistenceContext(name = "schoolPU")
	private EntityManager em;
	
	@Resource
	private SessionContext session;

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
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	@Override
	public boolean changeClass(Long idStudent, Long idClass) {
		// TODO Auto-generated method stub
		if(session.isCallerInRole("administrator")){
			Query query = em.createQuery("UPDATE " + Student.class.getName() + " SET CLASSNAME_ID = " + idClass + " WHERE ID = " + idStudent);
			query.executeUpdate();
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public boolean insertStudent(String studentFirstname, String studentLastName, ClassName className) {
		if(session.isCallerInRole("administrator")){		
			Student student = new Student();
			student.setFirstname(studentFirstname);
			student.setLastname(studentLastName);
			student.setClassName(className);
			em.persist(student);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean createMark(Subject subject, Student student, double value) {
		if(session.isCallerInRole("administrator")){		
			Mark mark = new Mark();
			mark.setSubject(subject);
			mark.setStudent(student);
			mark.setValue(value);
			em.persist(mark);
			return true;	
		} else {
			return false;
		}
	}
}
