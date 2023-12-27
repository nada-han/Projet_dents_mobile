package ma.ensa.backProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.ensa.backProject.dao.IDao;
import ma.ensa.backProject.entities.Groupe;
import ma.ensa.backProject.repository.GroupeRepository;

import java.util.List;

@Service
public class GroupeService implements IDao<Groupe> {

    @Autowired
    private GroupeRepository groupeRepository;

    @Override
    public Groupe create(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public Groupe update(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public Groupe findById(Long id) {
        return groupeRepository.findById(id.intValue()).orElse(null);
    }

    @Override
    public List<Groupe> findAll() {
        return groupeRepository.findAll();
    }

    @Override
    public boolean delete(Groupe groupe) {
        try {
            groupeRepository.delete(groupe);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
