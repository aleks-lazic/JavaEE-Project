package ch.hevs.test;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Test;

import ch.hevs.bankservice.Bank;
import ch.hevs.bankservice.ClassNameBean;
import ch.hevs.bankservice.IClassName;
import ch.hevs.businessobject.ClassName;
import junit.framework.Assert;

public class PersistenceTest {

//	@Test
//	public void testMethods() {
//		// use JNDI to inject reference to bank EJB
//		try {
//			InitialContext ctx = new InitialContext();
////			IClassName iClassName = (IClassName) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/BankBean!ch.hevs.bankservice.Bank");
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
//
//		ClassNameBean classNameBean = new ClassNameBean();
//		List<ClassName> classNameList = classNameBean.getAllClassName();
//
//		System.out.println(classNameList.size());
//
//		Assert.assertEquals(1, 1);
//	}
	/*
	 * @Test public void test() { EntityTransaction tx = null; try {
	 * EntityManagerFactory emf =
	 * Persistence.createEntityManagerFactory("schoolPU"); EntityManager em =
	 * emf.createEntityManager(); tx = em.getTransaction(); tx.begin(); // To
	 * complete ... ClassName className = new ClassName();
	 * className.setName("605_F");
	 * 
	 * Subject subject = new Subject(); subject.setName("Math");
	 * 
	 * Student student = new Student(); student.setFirstname("Flavien");
	 * student.setLastname("Lazic"); student.setClassName(className);
	 * 
	 * Mark mark = new Mark(); mark.setValue(5.1); mark.setStudent(student);
	 * mark.setSubject(subject);
	 * 
	 * em.persist(className); em.persist(subject); em.persist(student);
	 * em.persist(mark); tx.commit();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); try { tx.rollback(); } catch
	 * (IllegalStateException e1) { e1.printStackTrace(); } catch
	 * (SecurityException e1) { e1.printStackTrace(); } }
	 * 
	 * }
	 */
}
