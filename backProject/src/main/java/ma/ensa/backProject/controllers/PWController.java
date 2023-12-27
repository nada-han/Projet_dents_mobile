package ma.ensa.backProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ma.ensa.backProject.entities.PW;
import ma.ensa.backProject.service.PWService;

import java.util.List;

@RestController
@RequestMapping("/api/pws")
@CrossOrigin(origins = "http://localhost:3006")
public class PWController {

    @Autowired
    private PWService pwService;

    @PostMapping
    public ResponseEntity<PW> createPW(@RequestBody PW pw) {
        PW createdPW = pwService.create(pw);
        return new ResponseEntity<>(createdPW, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PW> getPWById(@PathVariable Long id) {
        PW pw = pwService.findById(id);
        return new ResponseEntity<>(pw, pw != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<PW>> getAllPWs() {
        List<PW> pws = pwService.findAll();
        return new ResponseEntity<>(pws, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PW> updatePW(@PathVariable Long id, @RequestBody PW pw) {
        PW existingPW = pwService.findById(id);
        if (existingPW != null) {
            pw.setId(existingPW.getId());
            PW updatedPW = pwService.update(pw);
            return new ResponseEntity<>(updatedPW, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePW(@PathVariable Long id) {
        PW pw = pwService.findById(id);
        if (pw != null) {
            pwService.delete(pw);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
