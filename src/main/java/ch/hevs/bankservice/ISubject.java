package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.ClassName;
import ch.hevs.businessobject.Subject;

@Local
public interface ISubject {

	List<Subject> getAllSubjects();

	boolean insertSubject(String name);

	Subject getSubjectByName(String name);
	
	boolean updateSubject(Long id, String name);

}
