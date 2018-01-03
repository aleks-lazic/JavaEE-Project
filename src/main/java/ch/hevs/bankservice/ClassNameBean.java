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
//		ClassName cn = (ClassName)em.createQuery("FROM " + ClassName.class.getName() + " c WHERE c.ID = :id").setParameter("id", id).getSingleResult();
//		System.out.println(cn.toString());
		ClassName cn = em.find(ClassName.class, id);
		return cn;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassName> getAllClassName() {
		List<ClassName> cns = (List<ClassName>)em.createQuery("SELECT c FROM " + ClassName.class.getName() + " c").getResultList();
		System.out.println("CNS SIZE : " + cns.size());
		return cns;
	}

	
	
}
