package ma.ensa.backProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ma.ensa.backProject.entities.Groupe;
import ma.ensa.backProject.service.GroupeService;

import java.util.List;

@RestController
@RequestMapping("/api/groupes")
@CrossOrigin(origins = "http://localhost:3006")
public class GroupeController {

    @Autowired
    private GroupeService groupeService;

    @PostMapping
    public ResponseEntity<Groupe> createGroupe(@RequestBody Groupe groupe) {
        Groupe createdGroupe = groupeService.create(groupe);
        return new ResponseEntity<>(createdGroupe, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Groupe> getGroupeById(@PathVariable Long id) {
        Groupe groupe = groupeService.findById(id);
        return new ResponseEntity<>(groupe, groupe != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Groupe>> getAllGroupes() {
        List<Groupe> groupes = groupeService.findAll();
        return new ResponseEntity<>(groupes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Groupe> updateGroupe(@PathVariable Long id, @RequestBody Groupe groupe) {
        Groupe existingGroupe = groupeService.findById(id);
        if (existingGroupe != null) {
            groupe.setId(existingGroupe.getId());
            Groupe updatedGroupe = groupeService.update(groupe);
            return new ResponseEntity<>(updatedGroupe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupe(@PathVariable Long id) {
        Groupe groupe = groupeService.findById(id);
        if (groupe != null) {
            groupeService.delete(groupe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
