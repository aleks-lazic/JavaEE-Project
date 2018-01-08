package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Local;
import javax.naming.NamingException;

import ch.hevs.businessobject.ClassName;
import ch.hevs.businessobject.Student;
import ch.hevs.businessobject.Subject;

@Local
public interface IStudent {

	List<Student> getAllStudents();

	Student getStudentById(Long id);
	
	Student getStudentByFirstnameAndLastname(String firstname, String lastname);
	
	boolean changeClass(String student, String className) throws NamingException;
	
	boolean insertStudent(String studentFirstname, String studentLastName, ClassName className);
	
	boolean createMark(Subject subject, Student student, double value);

}
