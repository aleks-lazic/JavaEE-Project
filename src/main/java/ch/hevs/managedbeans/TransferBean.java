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

	// variables update for the student transfer
	private Student updatedStudent;
	private ClassName updatedClass;

	// transactional result
	private String transactionResult = "";

	/**
	 * initialize the services
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
	}

	/**
	 * method to fill the database with some content
	 */
	public void fillDatabase() {
		// fill the database
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
		listStudentsString.removeAll(listStudentsString);
		for (int i = 0; i < listStudents.size(); i++) {
			listStudentsString.add(listStudents.get(i).getFirstname() + " " + listStudents.get(i).getLastname());
		}
	}

	/**
	 * create a list of string with the classes name to populate the dropdown
	 * list with the classes
	 */
	private void fillListClassesString() {
		listClassesString.removeAll(listClassesString);
		for (int i = 0; i < listClasses.size(); i++) {
			listClassesString.add(listClasses.get(i).getName());
		}
	}

	/**
	 * transfer a student from one class to another
	 * 
	 * @return
	 */
	public String transferStudent() {
		try {
			if (updatedStudent.getClass().getName().equals(updatedClass.getName())) {
				transactionResult = "error";
			} else {
				// Transfer the student
				iStudent.changeClass(updatedStudent.getId(), updatedClass.getId());
				transactionResult = "classList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			transactionResult = "exception";
		}
		return transactionResult;
	}

	/**
	 * create a subject with the subject name from the input form
	 * 
	 * @param subjectName
	 * @return
	 */
	public String createSubject(String subjectName) {
		iSubject.insertSubject(subjectName);
		getListSubjectsFromDB();
		return "subjectList";
	}

	/**
	 * updates the student on the change listener from the dropdown list in
	 * transferStudent
	 * 
	 * @param event
	 */
	public void updateStudent(ValueChangeEvent event) {
		System.out.println("new student : " + (String) event.getNewValue());
		String[] studentName = ((String) event.getNewValue()).split(" ");
		updatedStudent = iStudent.getStudentByFirstnameAndLastname(studentName[0], studentName[1]);
		System.out.println("student from db " + updatedStudent.getLastname());
	}

	/**
	 * updates the class on the change listener from the dropdown list in
	 * transferStudent
	 * 
	 * @param event
	 */
	public void updateClass(ValueChangeEvent event) {
		System.out.println("new class : " + (String) event.getNewValue());
		updatedClass = iClassName.getClassByName((String) event.getNewValue());
		System.out.println("class from db " + updatedClass.getId());
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
	 * get the list subjects from the db and creates the number of grades and
	 * the average grade
	 */
	private void getListSubjectsFromDB() {
		// get all subjects
		listSubjects = iSubject.getAllSubjects();
		// get average and nb grade per subject
		nbGradesPerSubject = new int[listSubjects.size()];
		averageGradePerSubject = new double[listSubjects.size()];

		// calculate average and nb grade
		calculateAverageAndNbGrades();
	}

	// navigation to pages
	/**
	 * navigate to the list of subjects
	 * 
	 * @return
	 */
	public String navigateToListSubjects() {
		getListSubjectsFromDB();
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

	public String getTransactionResult() {
		return transactionResult;
	}

	public void setTransactionResult(String transactionResult) {
		this.transactionResult = transactionResult;
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

	/*
	 * private List<Client> clients; private List<String> clientNames; private
	 * List<String> sourceAccountDescriptions; private List<String>
	 * destinationAccountDescriptions; private String sourceAccountDescription;
	 * private String destinationAccountDescription; private String
	 * sourceClientName; private String destinationClientName; private String
	 * transactionResult; private int transactionAmount; private Bank bank;
	 * 
	 * @PostConstruct public void initialize() throws NamingException {
	 * 
	 * // use JNDI to inject reference to bank EeJB InitialContext ctx = new
	 * InitialContext(); bank = (Bank) ctx.lookup(
	 * "java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/BankBean!ch.hevs.bankservice.Bank"
	 * );
	 * 
	 * // get clients // List<Client> clientList = bank.getClients(); //
	 * this.clientNames = new ArrayList<String>(); // for (Client client :
	 * clientList) { // this.clientNames.add(client.getLastname()); // }
	 * 
	 * // initialize account descriptions this.sourceAccountDescriptions = new
	 * ArrayList<String>(); this.destinationAccountDescriptions = new
	 * ArrayList<String>(); //List<Account> accounts =
	 * bank.getAccountListFromClientLastname(clientList.get(0).getLastname());
	 * //this.sourceAccountDescriptions.add(accounts.get(0).getDescription());
	 * //
	 * this.destinationAccountDescriptions.add(accounts.get(0).getDescription())
	 * ; }
	 * 
	 * // transactionAmount public int getTransactionAmount () { return
	 * transactionAmount; } public void setTransactionAmount (final int
	 * transactionAmount) { this.transactionAmount=transactionAmount; }
	 * 
	 * // sourceClientName public String getSourceClientName () { return
	 * sourceClientName; } public void setSourceClientName (final String
	 * sourceClientName) { this.sourceClientName=sourceClientName; }
	 * 
	 * // sourceAccountDescriptions public List<String>
	 * getSourceAccountDescriptions () { return sourceAccountDescriptions; }
	 * 
	 * // destinationAccountDescriptions public List<String>
	 * getDestinationAccountDescriptions () { return
	 * destinationAccountDescriptions; }
	 * 
	 * // destinationClientName public String getDestinationClientName () {
	 * return destinationClientName; } public void setDestinationClientName
	 * (final String destinationClientName) {
	 * this.destinationClientName=destinationClientName; }
	 * 
	 * // transactionResult public String getTransactionResult () { return
	 * transactionResult; } public void setTransactionResult(String
	 * transactionResult) { this.transactionResult = transactionResult; }
	 * 
	 * // sourceAccountDescription public String getSourceAccountDescription() {
	 * return sourceAccountDescription; } public void
	 * setSourceAccountDescription(String sourceAccountDescription) {
	 * this.sourceAccountDescription = sourceAccountDescription; }
	 * 
	 * // destinationAccountDescription public String
	 * getDestinationAccountDescription() { return
	 * destinationAccountDescription; } public void
	 * setDestinationAccountDescription( String destinationAccountDescription) {
	 * this.destinationAccountDescription = destinationAccountDescription; }
	 * 
	 * /* public void updateSourceAccounts(ValueChangeEvent event) {
	 * this.sourceClientName = (String)event.getNewValue();
	 * 
	 * List<Account> accounts =
	 * bank.getAccountListFromClientLastname(this.sourceClientName);
	 * this.sourceAccountDescriptions = new ArrayList<String>(); for (Account
	 * account : accounts) {
	 * this.sourceAccountDescriptions.add(account.getDescription()); } }
	 * 
	 * 
	 * public void updateDestinationAccounts(ValueChangeEvent event) {
	 * this.destinationClientName = (String)event.getNewValue();
	 * 
	 * List<Account> accounts =
	 * bank.getAccountListFromClientLastname(this.destinationClientName);
	 * this.destinationAccountDescriptions = new ArrayList<String>(); for
	 * (Account account : accounts) {
	 * this.destinationAccountDescriptions.add(account.getDescription()); } }
	 * 
	 * 
	 * public List<Client> getClients() { return clients; }
	 * 
	 * public List<String> getClientNames() { return clientNames; }
	 * 
	 * 
	 * public String performTransfer() {
	 * 
	 * try { if (sourceClientName.equals(destinationClientName) &&
	 * sourceAccountDescription.equals(destinationAccountDescription)) {
	 * 
	 * this.transactionResult="Error: accounts are identical!"; } else {
	 * 
	 * Account compteSrc = bank.getAccount(sourceAccountDescription,
	 * sourceClientName); Account compteDest =
	 * bank.getAccount(destinationAccountDescription, destinationClientName);
	 * 
	 * // Transfer bank.transfer(compteSrc, compteDest, transactionAmount);
	 * this.transactionResult="Success!"; } } catch (Exception e) {
	 * e.printStackTrace(); } return "showTransferResult"; // the String value
	 * returned represents the outcome used by the navigation handler to
	 * determine what page to display next. }
	 * 
	 */
}