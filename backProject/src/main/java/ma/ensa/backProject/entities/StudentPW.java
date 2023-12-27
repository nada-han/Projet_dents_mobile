package ma.ensa.backProject.entities;

import java.util.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StudentPW {
	
	@EmbeddedId
	private StudentPWKey id;
	@JoinColumn(name = "student", referencedColumnName = "id", insertable = false, updatable = false)
	@ManyToOne
	private Student student;
	@JoinColumn(name = "pw", referencedColumnName = "id", insertable = false, updatable = false)
	@ManyToOne
	private PW pw;
	
	private String time;
	private String imageFront;
	private String imageSide;
	private Date date;
	
	public StudentPW() {
		
	}
	
	public StudentPW(String time, String imageFront, String imageSide, Date date) {
		super();
		this.time = time;
		this.imageFront = imageFront;
		this.imageSide = imageSide;
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getImageFront() {
		return imageFront;
	}
	public void setImageFront(String imageFront) {
		this.imageFront = imageFront;
	}
	public String getImageSide() {
		return imageSide;
	}
	public void setImageSide(String imageSide) {
		this.imageSide = imageSide;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public StudentPWKey getId() {
		return id;
	}

	public void setId(StudentPWKey id) {
		this.id = id;
	}
	
	
	

}
