package ch.hevs.businessobject;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="Subject")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String name;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "subject")
	private List<Mark> marks;
	
	
	public Subject(){
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Mark> getMarks() {
		return marks;
	}
	public void setMarks(List<Mark> marks) {
		this.marks = marks;
	}
	
	
}
