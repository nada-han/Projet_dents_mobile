package ma.ensa.backProject.entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class PW {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	private String titre;
	private String objectif;
	private String docs;


	@ManyToMany (fetch = FetchType.EAGER)
	private List<Groupe> groupes;

	@ManyToOne
	private Tooth tooth;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getObjectif() {
		return objectif;
	}

	public void setObjectif(String objectif) {
		this.objectif = objectif;
	}

	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	public List<Groupe> getGroups() {
		return groupes;
	}

	public void setGroups(List<Groupe> groups) {
		this.groupes = groups;
	}

	public Tooth getTooth() {
		return tooth;
	}

	public void setTooth(Tooth tooth) {
		this.tooth = tooth;
	}
}