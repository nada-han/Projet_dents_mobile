package ma.ensa.backProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ma.ensa.backProject.entities.Tooth;
import ma.ensa.backProject.service.ToothService;

import java.util.List;

@RestController
@RequestMapping("/api/teeth")
@CrossOrigin(origins = "http://localhost:3006")
public class ToothController {

    @Autowired
    private ToothService toothService;

    @PostMapping
    public ResponseEntity<Tooth> createTooth(@RequestBody Tooth tooth) {
        Tooth createdTooth = toothService.create(tooth);
        return new ResponseEntity<>(createdTooth, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tooth> getToothById(@PathVariable Long id) {
        Tooth tooth = toothService.findById(id);
        return new ResponseEntity<>(tooth, tooth != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Tooth>> getAllTeeth() {
        List<Tooth> teeth = toothService.findAll();
        return new ResponseEntity<>(teeth, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tooth> updateTooth(@PathVariable Long id, @RequestBody Tooth tooth) {
        Tooth existingTooth = toothService.findById(id);
        if (existingTooth != null) {
            tooth.setId(existingTooth.getId());
            Tooth updatedTooth = toothService.update(tooth);
            return new ResponseEntity<>(updatedTooth, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTooth(@PathVariable Long id) {
        Tooth tooth = toothService.findById(id);
        if (tooth != null) {
            toothService.delete(tooth);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

