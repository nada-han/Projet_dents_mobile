package ma.ensa.backProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.ensa.backProject.dao.IDao;
import ma.ensa.backProject.entities.Admin;
import ma.ensa.backProject.repository.AdminRepository;

import java.util.List;

@Service
public class AdminService implements IDao<Admin> {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin update(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin findById(Long id) {
        return adminRepository.findById(id.intValue()).orElse(null);
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public boolean delete(Admin admin) {
        try {
            adminRepository.delete(admin);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Admin login(String userName, String password) {
        Admin admin = adminRepository.findByUserName(userName);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null; // Return null if login fails
    }
}
