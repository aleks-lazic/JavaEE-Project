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
		List<ClassName> cns = (List<ClassName>) em.createQuery("FROM " + ClassName.class.getName() + " c")
				.getResultList();
		System.out.println("CNS SIZE : " + cns.size());
		return cns;
	}

	@Override
	public void fillDatabase() {
		ClassName className1 = new ClassName();
		ClassName className2 = new ClassName();
		ClassName className3 = new ClassName();
		className1.setName("605_F");
		className2.setName("604_F");
		className3.setName("603_F");

		Subject subject1 = new Subject();
		Subject subject2 = new Subject();
		Subject subject3 = new Subject();
		subject1.setName("Math");
		subject2.setName("English");
		subject3.setName("German");

		Student student1 = new Student();
		Student student2 = new Student();
		Student student3 = new Student();
		Student student4 = new Student();
		Student student5 = new Student();
		student1.setFirstname("Aleksandar");
		student1.setLastname("Lazic");
		student1.setClassName(className1);
		student2.setFirstname("Flavien");
		student2.setLastname("Bonvin");
		student2.setClassName(className1);
		student3.setFirstname("Hugo");
		student3.setLastname("Rebelo");
		student3.setClassName(className2);
		student4.setFirstname("Kevin");
		student4.setLastname("Schmidt");
		student4.setClassName(className2);
		student5.setFirstname("Rafael");
		student5.setLastname("Peixoto");
		student5.setClassName(className3);

		Mark mark1 = new Mark();
		Mark mark2 = new Mark();
		Mark mark3 = new Mark();
		Mark mark4 = new Mark();
		Mark mark5 = new Mark();
		mark1.setValue(5.1);
		mark1.setStudent(student1);
		mark1.setSubject(subject1);
		mark2.setValue(4.7);
		mark2.setStudent(student2);
		mark2.setSubject(subject2);
		mark3.setValue(5.3);
		mark3.setStudent(student3);
		mark3.setSubject(subject3);
		mark4.setValue(5.5);
		mark4.setStudent(student4);
		mark4.setSubject(subject1);
		mark5.setValue(4.8);
		mark5.setStudent(student5);
		mark5.setSubject(subject2);

		em.persist(className1);
		em.persist(className2);
		em.persist(className3);

		em.persist(subject1);
		em.persist(subject2);
		em.persist(subject3);

		em.persist(student1);
		em.persist(student2);
		em.persist(student3);
		em.persist(student4);
		em.persist(student5);

		em.persist(mark1);
		em.persist(mark2);
		em.persist(mark3);
		em.persist(mark4);
		em.persist(mark5);
	}

	@Override
	public void clearDatabase() {
		// TODO Auto-generated method stub
		Query q = em.createNativeQuery("DELETE FROM CLASSNAME");
		Query q2 = em.createNativeQuery("DELETE FROM SUBJECT");
		q.executeUpdate();
		q2.executeUpdate();
	}

	@Override
	public ClassName getClassByName(String name) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("FROM " + ClassName.class.getName() + " s WHERE s.name = :name");
		query.setParameter("name", name);
		return (ClassName) query.getSingleResult();
	}
}
