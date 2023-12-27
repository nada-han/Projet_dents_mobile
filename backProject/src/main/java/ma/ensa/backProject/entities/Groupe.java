package ma.ensa.backProject.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Groupe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String code;
	private String year;
	
	@ManyToOne
	private Professor professor;
	
	@ManyToMany (mappedBy = "groupes", fetch = FetchType.EAGER)
	private List<PW> pws;
	
	@ManyToMany (mappedBy = "groupes", fetch = FetchType.EAGER)
	private List<Student> students;
	
	public Groupe() {
		
	}
	
	public Groupe(int id, String code, String year) {
		super();
		this.id = id;
		this.code = code;
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	
	

}
