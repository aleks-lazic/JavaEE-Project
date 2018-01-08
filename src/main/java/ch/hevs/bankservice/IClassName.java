package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.*;

@Local
public interface IClassName {

	ClassName getClassName(Long id);
	
	ClassName getClassByName(String name);
	
	List<ClassName> getAllClassName();
	
	void fillDatabase();
	
	void clearDatabase();
	
	boolean deleteById(ClassName className);
	
	boolean insertClass(String className);
}
