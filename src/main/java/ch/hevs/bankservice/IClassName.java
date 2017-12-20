package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.*;

@Local
public interface IClassName {

//	Account getAccount(String accountDescription, String lastnameOwner);

	ClassName getClassName(Long id);
	
	List<ClassName> getAllClassName();
	
}
