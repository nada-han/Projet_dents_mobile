package ma.ensa.backProject.controllers;

import ma.ensa.backProject.entities.Angle;
import ma.ensa.backProject.service.AngleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/angles")
public class AngleController {

    @Autowired
    private AngleService angleService;  // Add @Autowired annotation here

    @GetMapping("/all")
    public List<Angle> getAll(){
        return angleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getByID (@PathVariable Long id){
        Angle angle = angleService.findById(id);
        if(angle == null)
            return new ResponseEntity<Object>("Angle n'existe pas ", HttpStatus.NOT_FOUND);
        else
            return ResponseEntity.ok(angle);
    }

    @PostMapping("/create")
    public Angle create(@RequestBody Angle angle){  // Fix the method name to 'create' instead of 'crate'
        angle.setId(0L);
        return angleService.create(angle);
    }
}
