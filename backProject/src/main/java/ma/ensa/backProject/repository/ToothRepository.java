package ma.ensa.backProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ensa.backProject.entities.Tooth;

@Repository
public interface ToothRepository extends JpaRepository<Tooth, Integer> {
    // Additional custom queries can be added here if needed
}

