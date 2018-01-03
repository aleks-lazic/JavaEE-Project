package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.Student;

@Local
public interface IStudent {

	List<Student> getAllStudents();

	Student getStudentById(Long id);

}
