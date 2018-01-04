package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.hevs.businessobject.Subject;

@Stateless
public class SubjectBean implements ISubject {

	@PersistenceContext(name = "schoolPU")
	private EntityManager em;

	@Override
	public List<Subject> getAllSubjects() {
		return (List<Subject>) em.createQuery("FROM " + Subject.class.getName()).getResultList();
	}

	@Override
	public void insertSubject(String name) {
		Subject subject = new Subject();
		subject.setName(name);
		em.persist(subject);
	}

}
