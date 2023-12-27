package ma.ensa.backProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ensa.backProject.entities.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    // Additional custom queries can be added here if needed
}
