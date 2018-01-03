package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.*;

@Local
public interface IClassName {

	ClassName getClassName(Long id);
	
	List<ClassName> getAllClassName();
	
	void fillDatabase();
	
	void clearDatabase();
	
}
