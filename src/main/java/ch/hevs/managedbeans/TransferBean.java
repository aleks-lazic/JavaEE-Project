package ch.hevs.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.bankservice.IClassName;
import ch.hevs.bankservice.IStudent;
import ch.hevs.businessobject.ClassName;
import ch.hevs.businessobject.Student;

/**
 * TransferBean.java
 * 
 */

public class TransferBean {

	// service interfaces
	private IClassName iClassName;
	private IStudent iStudent;

	// list classes and list students
	private List<ClassName> listClasses;
	private List<Student> listStudents;

	// list classes and students string
	private List<String> listClassesString;
	private List<String> listStudentsString;

	// variables update for the student transfer
	private Student updatedStudent;
	private ClassName updatedClass;

	// transactional result
	private String transactionResult = "";

	@PostConstruct
	public void initialize() throws NamingException {
		// use JNDI to inject reference to bank EJB
		InitialContext ctx = new InitialContext();
		iClassName = (IClassName) ctx.lookup(
				"java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/ClassNameBean!ch.hevs.bankservice.IClassName");
		iStudent = (IStudent) ctx
				.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/StudentBean!ch.hevs.bankservice.IStudent");

		// initialize strings list
		listClassesString = new ArrayList<String>();
		listStudentsString = new ArrayList<String>();
	}

	public void fillDatabase() {
		// fill the database
		iClassName.fillDatabase();
		// get all classes for the school
		listClasses = iClassName.getAllClassName();
		// get all students
		listStudents = iStudent.getAllStudents();
		fillListClassesString();
		fillListStudentString();
	}

	public void clearDatabase() {
		iClassName.clearDatabase();
	}

	private void fillListStudentString() {
		for (int i = 0; i < listStudents.size(); i++) {
			listStudentsString.add(listStudents.get(i).getFirstname() + " " + listStudents.get(i).getLastname());
		}
	}

	private void fillListClassesString() {
		for (int i = 0; i < listClasses.size(); i++) {
			listClassesString.add(listClasses.get(i).getName());
		}
	}

	public void transferStudent() {
		try {
			if (updatedStudent.getClass().getName().equals(updatedClass.getName())) {
				this.transactionResult = "The student is already in this class !";
			} else {
				// Transfer the student
				iStudent.changeClass(updatedStudent.getId(), updatedClass.getId());
				this.transactionResult = "Success!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public void updateStudent(ValueChangeEvent event) {
		System.out.println("new student : " + (String) event.getNewValue());
		String[] studentName = ((String) event.getNewValue()).split(" ");
		updatedStudent = iStudent.getStudentByFirstnameAndLastname(studentName[0], studentName[1]);
		System.out.println("student from db " + updatedStudent.getLastname());
	}

	public void updateClass(ValueChangeEvent event) {
		System.out.println("new class : " + (String) event.getNewValue());
		updatedClass = iClassName.getClassByName((String) event.getNewValue());
		System.out.println("class from db " + updatedClass.getId());
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