package ma.ensa.backProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.ensa.backProject.dao.IDao;
import ma.ensa.backProject.entities.StudentPW;
import ma.ensa.backProject.repository.StudentPWRepository;

import java.util.List;

@Service
public class StudentPWService implements IDao<StudentPW> {

    @Autowired
    private StudentPWRepository studentPWRepository;

    @Override
    public StudentPW create(StudentPW studentPW) {
        return studentPWRepository.save(studentPW);
    }

    @Override
    public StudentPW update(StudentPW studentPW) {
        return studentPWRepository.save(studentPW);
    }

    @Override
    public StudentPW findById(Long id) {
        // Note: StudentPW uses composite key, so appropriate method needs to be implemented in repository
        // Example: studentPWRepository.findById(new StudentPWKey(id, pwId)).orElse(null);
        return null;
    }

    @Override
    public List<StudentPW> findAll() {
        return studentPWRepository.findAll();
    }

    @Override
    public boolean delete(StudentPW studentPW) {
        try {
            studentPWRepository.delete(studentPW);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
