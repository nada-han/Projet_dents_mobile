package ma.ensa.backProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.ensa.backProject.dao.IDao;
import ma.ensa.backProject.entities.Tooth;
import ma.ensa.backProject.repository.ToothRepository;

import java.util.List;

@Service
public class ToothService implements IDao<Tooth> {

    @Autowired
    private ToothRepository toothRepository;

    @Override
    public Tooth create(Tooth tooth) {
        return toothRepository.save(tooth);
    }

    @Override
    public Tooth update(Tooth tooth) {
        return toothRepository.save(tooth);
    }

    @Override
    public Tooth findById(Long id) {
        return toothRepository.findById(id.intValue()).orElse(null);
    }

    @Override
    public List<Tooth> findAll() {
        return toothRepository.findAll();
    }

    @Override
    public boolean delete(Tooth tooth) {
        try {
            toothRepository.delete(tooth);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
