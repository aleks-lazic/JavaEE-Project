package ch.hevs.businessobject;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="Student")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String firstname;
	private String lastname;
	@ManyToOne
	private ClassName className;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy="student")
	private List<Mark> marks;

	
	public Student(){
		
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public ClassName getClassName() {
		return className;
	}


	public void setClassName(ClassName className) {
		this.className = className;
	}


	public List<Mark> getMarks() {
		return marks;
	}


	public void setMarks(List<Mark> marks) {
		this.marks = marks;
	}
	
	
	
	
}
