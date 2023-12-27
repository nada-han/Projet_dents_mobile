package ma.ensa.backProject.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Student extends User{
	
	private String number;
	
	@OneToMany (mappedBy = "student")
	private List<StudentPW> studentPWs;
	
	@ManyToMany (fetch = FetchType.EAGER)
	private List<Groupe> groupes;
	
	public Student() {
		
	}

	public Student(String number) {
		super();
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	

}
