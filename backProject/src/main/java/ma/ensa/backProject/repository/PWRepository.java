package ma.ensa.backProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ensa.backProject.entities.PW;

@Repository
public interface PWRepository extends JpaRepository<PW, Integer> {
    // Additional custom queries can be added here if needed
}
