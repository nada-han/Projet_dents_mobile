package ma.ensa.backProject.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.ensa.backProject.dao.IDao;
import ma.ensa.backProject.entities.PW;
import ma.ensa.backProject.repository.PWRepository;

import java.util.List;

@Service
public class PWService implements IDao<PW> {

    @Autowired
    private PWRepository pwRepository;

    @Override
    public PW create(PW pw) {
        return pwRepository.save(pw);
    }

    @Override
    public PW update(PW pw) {
        return pwRepository.save(pw);
    }

    @Override
    public PW findById(Long id) {
        return pwRepository.findById(id.intValue()).orElse(null);
    }

    @Override
    public List<PW> findAll() {
        return pwRepository.findAll();
    }

    @Override
    public boolean delete(PW pw) {
        try {
            pwRepository.delete(pw);
            return true;
        } catch (Exception e) {
            return false;
        }
    }}

