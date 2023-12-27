package ma.ensa.backProject.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class StudentPWKey {
	private int Student;
	private int PW;
	
	
	
	public StudentPWKey() {
		super();
	}



	public StudentPWKey(int student, int pW) {
		super();
		Student = student;
		PW = pW;
	}



	public int getStudent() {
		return Student;
	}



	public void setStudent(int student) {
		Student = student;
	}



	public int getPW() {
		return PW;
	}



	public void setPW(int pW) {
		PW = pW;
	}
	
	
	
	
	

}
