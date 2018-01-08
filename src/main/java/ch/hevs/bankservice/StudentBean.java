package ch.hevs.bankservice;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ch.hevs.businessobject.ClassName;
import ch.hevs.businessobject.Mark;
import ch.hevs.businessobject.Student;
import ch.hevs.businessobject.Subject;

@Stateless
public class StudentBean implements IStudent{

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
	public boolean changeClass(String student, String className) throws NamingException {
		if (session.isCallerInRole("administrator")) {
			InitialContext ctx = new InitialContext();
			IClassName iClass = (IClassName) ctx
					.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/ClassNameBean!ch.hevs.bankservice.IClassName");

			
			// get the student and the class from the db
			Student s = getStudentByFirstnameAndLastname(student.split(" ")[0], student.split(" ")[1]);
			ClassName oldClass = s.getClassName();
			ClassName newClass = iClass.getClassByName(className);

			// remove the student from the class and add to the new one
			oldClass.removeStudent(s);
			newClass.addStudent(s);
			s.setClassName(newClass);

			// modify in the db
			em.merge(s);

			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean insertStudent(String studentFirstname, String studentLastName, ClassName className) {
		if (session.isCallerInRole("administrator")) {
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
		if (session.isCallerInRole("administrator")) {
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
