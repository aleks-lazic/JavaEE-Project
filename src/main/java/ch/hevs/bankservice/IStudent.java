package ch.hevs.bankservice;

import java.util.List;

import ch.hevs.businessobject.Student;

public interface IStudent {
	
	List<Student> getAllStudents();
	Student getStudentById(Long id);

}
