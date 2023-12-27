package ma.ensa.backProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ensa.backProject.entities.Groupe;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Integer> {
    // Additional custom queries can be added here if needed
}
