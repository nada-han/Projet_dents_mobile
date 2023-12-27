package ma.ensa.backProject.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Professor extends User{
	
	private String grade;
	
	@OneToMany
	private List<Groupe> groupes;

	public Professor() {
		super();
	}



	public Professor(String grade) {
		super();
		this.grade = grade;
	}



	public String getGrade() {
		return grade;
	}



	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
	
	
	
	

}
