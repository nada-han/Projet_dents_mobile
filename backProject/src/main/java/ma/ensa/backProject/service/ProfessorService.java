package ma.ensa.backProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.ensa.backProject.dao.IDao;
import ma.ensa.backProject.entities.Professor;
import ma.ensa.backProject.repository.ProfessorRepository;

import java.util.List;

@Service
public class ProfessorService implements IDao<Professor> {

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public Professor create(Professor professor) {
        return professorRepository.save(professor);
    }

    @Override
    public Professor update(Professor professor) {
        return professorRepository.save(professor);
    }

    @Override
    public Professor findById(Long id) {
        return professorRepository.findById(id.intValue()).orElse(null);
    }

    @Override
    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    @Override
    public boolean delete(Professor professor) {
        try {
            professorRepository.delete(professor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
