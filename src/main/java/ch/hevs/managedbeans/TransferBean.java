package ch.hevs.managedbeans;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.bankservice.IClassName;
import ch.hevs.bankservice.IStudent;
import ch.hevs.bankservice.ISubject;
import ch.hevs.businessobject.ClassName;
import ch.hevs.businessobject.Student;
import ch.hevs.businessobject.Subject;

/**
 * TransferBean.java
 * 
 */

public class TransferBean {

	// service interfaces
	private IClassName iClassName;
	private IStudent iStudent;
	private ISubject iSubject;

	// list of entities
	private List<ClassName> listClasses;
	private List<Student> listStudents;
	private List<Subject> listSubjects;

	// subjects properties
	private int[] nbGradesPerSubject;
	private double[] averageGradePerSubject;

	// list classes and students string
	private List<String> listClassesString;
	private List<String> listStudentsString;
	private List<String> listSubjectString;

	// variables update
	private String updatedStudentString;
	private String updatedClassString;
	private String updatedSubjectString;
	private Student updatedStudent;
	private ClassName updatedClass;
	private Subject udpatedSubject;

	// variables for the edition of a subject
	private String idSubjectToUpdate;
	private String nameSubjectToUpdate;

	// transactional result
	private String transactionResult = "";

	/**
	 * initialize the services with the JNDI
	 * 
	 * @throws NamingException
	 */
	@PostConstruct
	public void initialize() throws NamingException {
		// use JNDI to inject reference to bank EJB
		InitialContext ctx = new InitialContext();
		iClassName = (IClassName) ctx.lookup(
				"java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/ClassNameBean!ch.hevs.bankservice.IClassName");
		iStudent = (IStudent) ctx
				.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/StudentBean!ch.hevs.bankservice.IStudent");
		iSubject = (ISubject) ctx
				.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/SubjectBean!ch.hevs.bankservice.ISubject");

		// initialize strings list
		listClassesString = new ArrayList<String>();
		listStudentsString = new ArrayList<String>();
		listSubjectString = new ArrayList<String>();
	}

	/**
	 * method to fill the database with some content
	 */
	public void fillDatabase() {
		// fill the database
		iClassName.clearDatabase();
		iClassName.fillDatabase();
	}

	/**
	 * method to clear all data from the db
	 */
	public void clearDatabase() {
		iClassName.clearDatabase();
	}

	/**
	 * create a list of string with the students name to populate the dropdown
	 * list with the names
	 */
	private void fillListStudentString() {
		if (listStudents == null) {
			listStudents = iStudent.getAllStudents();
		}
		listStudentsString = new ArrayList<String>();
		for (int i = 0; i < listStudents.size(); i++) {
			listStudentsString.add(listStudents.get(i).getFirstname() + " " + listStudents.get(i).getLastname());
		}
	}

	/**
	 * create a list of string with the classes name to populate the dropdown
	 * list with the classes
	 */
	private void fillListClassesString() {
		if (listClasses == null) {
			listClasses = iClassName.getAllClassName();
		}
		listClassesString = new ArrayList<String>();
		for (int i = 0; i < listClasses.size(); i++) {
			listClassesString.add(listClasses.get(i).getName());
		}
	}

	/**
	 * create a list of string with the students name to populate the dropdown
	 * list with the names
	 */
	private void fillListSubjectString() {
		if (listSubjects == null) {
			listSubjects = iSubject.getAllSubjects();
		}
		listSubjectString.removeAll(listSubjectString);
		for (int i = 0; i < listSubjects.size(); i++) {
			listSubjectString.add(listSubjects.get(i).getName());
		}
	}

	/**
	 * transfer a student from one class to another
	 * 
	 * @return
	 */
	public String transferStudent() {
		try {
			// Transfer the student
			if (!iStudent.changeClass(updatedStudentString, updatedClassString)) {
				return navigateToErrorPage();
			}
			return navigateToListClasses();
		} catch (Exception e) {
			e.printStackTrace();
			return navigateToFormatError();
		}
	}

	/**
	 * create a subject with the subject name from the input form
	 * 
	 * @param subjectName
	 * @return
	 */
	public String createSubject(String subjectName) {
		if (!subjectName.equals("")) {
			if (!iSubject.insertSubject(subjectName)) {
				return navigateToErrorPage();
			}
		} else {
			return navigateToFormatError();
		}
		return navigateToListSubjects();
	}

	/**
	 * create a student with the subject name from the input form
	 * 
	 * @param subjectName
	 * @return
	 */
	public String createStudent(String studentFirstname, String studentLastName) {
		ClassName className = new ClassName();

		if (!studentFirstname.equals("") && !studentLastName.equals("")) {
			className = iClassName.getClassByName(updatedClassString);
			if (!iStudent.insertStudent(studentFirstname, studentLastName, className)) {
				return navigateToErrorPage();
			}
		} else {
			return navigateToFormatError();
		}
		return navigateToListStudents();
	}

	/**
	 * create a mark with the subject name from the input form
	 * 
	 * @param subjectName
	 * @return
	 */
	public String createNewMark(String mark) {
		Subject subject = new Subject();
		Student student = new Student();
		subject = iSubject.getSubjectByName(updatedSubjectString);
		String[] studentName = updatedStudentString.split(" ");
		student = iStudent.getStudentByFirstnameAndLastname(studentName[0], studentName[1]);

		if (!mark.equals("")) {
			double value = Double.parseDouble(mark);
			if (!iStudent.createMark(subject, student, value)) {
				return navigateToErrorPage();
			}
		} else {
			return navigateToFormatError();
		}

		return navigateToListStudents();
	}

	public String createClass(String className) {
		if (!className.equals("")) {
			if (!iClassName.insertClass(className)) {
				return navigateToErrorPage();
			} else {
				return navigateToListClasses();
			}
		} else {
			return navigateToFormatError();
		}
	}

	/**
	 * Delete a class
	 * 
	 * @param classID
	 * @return
	 */
	public String deleteClass(String classID) {
		ClassName className = iClassName.getClassName(Long.parseLong(classID));
		if (!iClassName.deleteById(className)) {
			return navigateToErrorPage();
		}
		return navigateToListClasses();
	}

	public String updateSubjectDB(String newSubjectName) {
		if (!iSubject.updateSubject(Long.parseLong(idSubjectToUpdate), newSubjectName)) {
			return navigateToErrorPage();
		}
		return navigateToListSubjects();
	}

	/**
	 * updates the student on the change listener from the dropdown list in
	 * transferStudent
	 * 
	 * @param event
	 */
	public void updateStudent(ValueChangeEvent event) {
		updatedStudentString = (String) event.getNewValue();
	}

	/**
	 * updates the class on the change listener from the dropdown list in
	 * transferStudent
	 * 
	 * @param event
	 */
	public void updateClass(ValueChangeEvent event) {
		updatedClassString = (String) event.getNewValue();
	}

	/**
	 * updates the subject on the change listener from the dropdown list in
	 * addmarktouser
	 * 
	 * @param event
	 */
	public void updateSubject(ValueChangeEvent event) {
		updatedSubjectString = (String) event.getNewValue();
	}

	/**
	 * calculate the average and the number of grades for each subject
	 */
	private void calculateAverageAndNbGrades() {
		double average = 0.0;
		double sum = 0.0;
		for (int i = 0; i < listSubjects.size(); i++) {
			for (int j = 0; j < listSubjects.get(i).getMarks().size(); j++) {
				nbGradesPerSubject[i]++;
				sum += listSubjects.get(i).getMarks().get(j).getValue();
			}
			average = sum / listSubjects.get(i).getMarks().size();
			averageGradePerSubject[i] = average;
			average = 0.0;
			sum = 0.0;
		}
	}

	/**
	 * navigation to the home page
	 * 
	 * @return
	 */
	public String navigateToHomePage() {
		return "welcomeBank";
	}

	/**
	 * navigate to the list of subjects
	 * 
	 * @return
	 */
	public String navigateToListSubjects() {
		// get all subjects
		listSubjects = iSubject.getAllSubjects();
		// get average and nb grade per subject
		nbGradesPerSubject = new int[listSubjects.size()];
		averageGradePerSubject = new double[listSubjects.size()];
		// calculate average and nb grade
		calculateAverageAndNbGrades();
		return "subjectList";
	}

	/**
	 * navigate to the list of classes
	 * 
	 * @return
	 */
	public String navigateToListClasses() {
		listClasses = iClassName.getAllClassName();
		return "classList";
	}

	/**
	 * navigate to the list of students
	 * 
	 * @return
	 */
	public String navigateToListStudents() {
		listStudents = iStudent.getAllStudents();
		return "studentList";
	}

	/**
	 * navigate to the page transfer student and populate the list of classes
	 * and students for the dropdown
	 * 
	 * @return
	 */
	public String navigateToTransferStudent() {
		fillListClassesString();
		fillListStudentString();
		return "transferStudent";
	}

	/**
	 * navigate to the page to create a subject
	 * 
	 * @return
	 */
	public String navigateToCreateSubject() {
		return "createSubject";
	}

	/**
	 * navigate to the page to create a student
	 * 
	 * @return
	 */
	public String navigateToCreateStudent() {
		fillListClassesString();
		return "createStudent";
	}

	/**
	 * navigate to the page to create a mark to a student
	 * 
	 * @return
	 */
	public String navigateToAddMarkToStudent() {
		fillListSubjectString();
		fillListStudentString();
		return "addMarkToStudent";
	}

	public String navigateToUpdateSubject(String id, String name) {
		idSubjectToUpdate = id;
		nameSubjectToUpdate = name;
		return "subjectUpdate";
	}

	public String navigateToFormatError() {
		return "errorPageFormatting";
	}

	public String navigateToErrorPage() {
		return "errorpage";
	}

	public String navigateToCreateClass() {
		return "createClass";
	}

	// getters and setters
	public IClassName getiClassName() {
		return iClassName;
	}

	public void setiClassName(IClassName iClassName) {
		this.iClassName = iClassName;
	}

	public List<ClassName> getListClasses() {
		return listClasses;
	}

	public void setListClasses(List<ClassName> listClasses) {
		this.listClasses = listClasses;
	}

	public List<Student> getListStudents() {
		return listStudents;
	}

	public void setListStudents(List<Student> listStudents) {
		this.listStudents = listStudents;
	}

	public IStudent getiStudent() {
		return iStudent;
	}

	public void setiStudent(IStudent iStudent) {
		this.iStudent = iStudent;
	}

	public List<String> getListClassesString() {
		return listClassesString;
	}

	public void setListClassesString(List<String> listClassesString) {
		this.listClassesString = listClassesString;
	}

	public List<String> getListStudentsString() {
		return listStudentsString;
	}

	public void setListStudentsString(List<String> listStudentsString) {
		this.listStudentsString = listStudentsString;
	}

	public Student getUpdatedStudent() {
		return updatedStudent;
	}

	public void setUpdatedStudent(Student updatedStudent) {
		this.updatedStudent = updatedStudent;
	}

	public ClassName getUpdatedClass() {
		return updatedClass;
	}

	public void setUpdatedClass(ClassName updatedClass) {
		this.updatedClass = updatedClass;
	}

	public ISubject getiSubject() {
		return iSubject;
	}

	public void setiSubject(ISubject iSubject) {
		this.iSubject = iSubject;
	}

	public List<Subject> getListSubjects() {
		return listSubjects;
	}

	public void setListSubjects(List<Subject> listSubjects) {
		this.listSubjects = listSubjects;
	}

	public int[] getNbGradesPerSubject() {
		return nbGradesPerSubject;
	}

	public void setNbGradesPerSubject(int[] nbGradesPerSubject) {
		this.nbGradesPerSubject = nbGradesPerSubject;
	}

	public double[] getAverageGradePerSubject() {
		return averageGradePerSubject;
	}

	public void setAverageGradePerSubject(double[] averageGradePerSubject) {
		this.averageGradePerSubject = averageGradePerSubject;
	}

	public List<String> getListSubjectString() {
		return listSubjectString;
	}

	public void setListSubjectString(List<String> listSubjectString) {
		this.listSubjectString = listSubjectString;
	}

	public String getUpdatedStudentString() {
		return updatedStudentString;
	}

	public void setUpdatedStudentString(String updatedStudentString) {
		this.updatedStudentString = updatedStudentString;
	}

	public String getUpdatedClassString() {
		return updatedClassString;
	}

	public void setUpdatedClassString(String updatedClassString) {
		this.updatedClassString = updatedClassString;
	}

	public String getUpdatedSubjectString() {
		return updatedSubjectString;
	}

	public void setUpdatedSubjectString(String updatedSubjectString) {
		this.updatedSubjectString = updatedSubjectString;
	}

	public Subject getUdpatedSubject() {
		return udpatedSubject;
	}

	public void setUdpatedSubject(Subject udpatedSubject) {
		this.udpatedSubject = udpatedSubject;
	}

	public String getIdSubjectToUpdate() {
		return idSubjectToUpdate;
	}

	public void setIdSubjectToUpdate(String idSubjectToUpdate) {
		this.idSubjectToUpdate = idSubjectToUpdate;
	}

	public String getNameSubjectToUpdate() {
		return nameSubjectToUpdate;
	}

	public void setNameSubjectToUpdate(String nameSubjectToUpdate) {
		this.nameSubjectToUpdate = nameSubjectToUpdate;
	}
}