package ch.hevs.businessobject;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ClassName")
public class ClassName {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String name;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "className", fetch = FetchType.EAGER)
	private List<Student> students;

	public ClassName() {

	}

	public ClassName(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void removeStudent(Student student){
		students.remove(student);
	}
	
	public void addStudent(Student student){
		students.add(student);
	}
	
	@Override
	public String toString() {
		String s = "ClassName [id=" + id + ", name=" + name + ", students=\n" ;
		for(int i = 0 ; i<students.size() ; i++){
			s += "\t" + students.get(i).toString() + "\n";
		}
		return s;
	}

}
