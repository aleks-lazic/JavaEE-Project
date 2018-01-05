package ch.hevs.bankservice;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ch.hevs.businessobject.Student;
import ch.hevs.businessobject.Subject;

@Stateless
@RolesAllowed(value = {"administrator", "student"})
public class SubjectBean implements ISubject {

	@PersistenceContext(name = "schoolPU")
	private EntityManager em;

	@Resource
	private SessionContext session;
	
	@Override
	public List<Subject> getAllSubjects() {
		return (List<Subject>) em.createQuery("FROM " + Subject.class.getName()).getResultList();
	}
	
	@Override
	public boolean insertSubject(String name) {
		if(session.isCallerInRole("administrator")){		
			Subject subject = new Subject();
			subject.setName(name);
			em.persist(subject);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Subject getSubjectByName(String name) {
		Query query = em.createQuery("FROM " + Subject.class.getName() + " s WHERE s.name = :name");
		query.setParameter("name", name);
		return (Subject) query.getSingleResult();
	}

	@Override
	public boolean updateSubject(Long id, String name) {
		if(session.isCallerInRole("administrator")){		
			Query query = em.createQuery("UPDATE " + Subject.class.getName() + " SET NAME =:name WHERE ID = " + id);
			query.setParameter("name", name);
			query.executeUpdate();
			return true;
		} else {
			return false;
		}
	}
}
