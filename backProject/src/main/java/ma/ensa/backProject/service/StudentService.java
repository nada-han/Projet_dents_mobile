package ma.ensa.backProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.ensa.backProject.dao.IDao;
import ma.ensa.backProject.entities.Student;
import ma.ensa.backProject.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService implements IDao<Student> {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student update(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id.intValue()).orElse(null);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public boolean delete(Student student) {
        try {
            studentRepository.delete(student);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

