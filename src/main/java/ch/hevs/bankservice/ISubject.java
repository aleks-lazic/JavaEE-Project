package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.Subject;

@Local
public interface ISubject {

	List<Subject> getAllSubjects();

	void insertSubject(String name);

}
