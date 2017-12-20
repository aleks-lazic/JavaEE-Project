package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.hevs.businessobject.ClassName;

@Stateless
public class ClassNameBean implements IClassName{

	@PersistenceContext(name = "schoolPU")
	private EntityManager em;

	
	@Override
	public ClassName getClassName(Long id) {
		return (ClassName)em.createQuery("SELECT * FROM CLASSNAME c WHERE c.ID = id").setParameter("id", id).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassName> getAllClassName() {
		return (List<ClassName>)em.createQuery("SELECT * FROM CLASSNAME").getResultList();
	}

	
	
}
