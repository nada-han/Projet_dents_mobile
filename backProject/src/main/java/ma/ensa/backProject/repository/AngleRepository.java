package ma.ensa.backProject.repository;
import ma.ensa.backProject.entities.Angle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AngleRepository extends JpaRepository<Angle, Long> {
    // Ajoutez des méthodes personnalisées si nécessaire
}

