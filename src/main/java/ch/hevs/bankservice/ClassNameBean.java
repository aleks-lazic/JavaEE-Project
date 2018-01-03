package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ch.hevs.businessobject.ClassName;
import ch.hevs.businessobject.Mark;
import ch.hevs.businessobject.Student;
import ch.hevs.businessobject.Subject;

@Stateless
public class ClassNameBean implements IClassName {

	@PersistenceContext(name = "schoolPU")
	private EntityManager em;

	@Override
	public ClassName getClassName(Long id) {
		// ClassName cn = (ClassName)em.createQuery("FROM " +
		// ClassName.class.getName() + " c WHERE c.ID = :id").setParameter("id",
		// id).getSingleResult();
		// System.out.println(cn.toString());
		ClassName cn = em.find(ClassName.class, id);
		return cn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassName> getAllClassName() {
		List<ClassName> cns = (List<ClassName>) em.createQuery("SELECT c FROM " + ClassName.class.getName() + " c")
				.getResultList();
		System.out.println("CNS SIZE : " + cns.size());
		return cns;
	}

	@Override
	public void fillDatabase() {
		ClassName className = new ClassName();
		className.setName("605_F");

		Subject subject = new Subject();
		subject.setName("Math");

		Student student = new Student();
		student.setFirstname("Flavien");
		student.setLastname("Lazic");
		student.setClassName(className);

		Mark mark = new Mark();
		mark.setValue(5.1);
		mark.setStudent(student);
		mark.setSubject(subject);

		em.persist(className);
		em.persist(subject);
		em.persist(student);
		em.persist(mark);
	}

	@Override
	public void clearDatabase() {
		// TODO Auto-generated method stub
		Query q = em.createNativeQuery("DELETE FROM CLASSNAME");
		Query q2 = em.createNativeQuery("DELETE FROM SUBJECT");
		q.executeUpdate();
		q2.executeUpdate();
	}
}
