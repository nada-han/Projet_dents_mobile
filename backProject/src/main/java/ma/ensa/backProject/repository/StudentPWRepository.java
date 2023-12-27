package ma.ensa.backProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ensa.backProject.entities.StudentPW;
import ma.ensa.backProject.entities.StudentPWKey;

@Repository
public interface StudentPWRepository extends JpaRepository<StudentPW, StudentPWKey> {
    // Additional custom queries can be added here if needed
}
