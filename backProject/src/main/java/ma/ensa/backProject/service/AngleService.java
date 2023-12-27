package ma.ensa.backProject.service;

import ma.ensa.backProject.dao.IDao;
import ma.ensa.backProject.entities.Angle;
import ma.ensa.backProject.repository.AngleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AngleService implements IDao<Angle> {

    @Autowired
    private AngleRepository angleRepository;  // Add @Autowired annotation here

    @Override
    public Angle create(Angle o) {
        return angleRepository.save(o);
    }

    @Override
    public Angle update(Angle o) {
        // Implement your update logic here
        return null;
    }

    @Override
    public Angle findById(Long id) {
        return angleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Angle> findAll() {
        return angleRepository.findAll();
    }

    @Override
    public boolean delete(Angle o) {
        // Implement your delete logic here
        return false;
    }
}
