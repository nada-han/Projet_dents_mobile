package ma.ensa.backProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ensa.backProject.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Additional custom queries can be added here if needed
}
