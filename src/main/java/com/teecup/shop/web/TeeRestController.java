package com.teecup.shop.web;
 
import com.teecup.shop.model.Tee;
import com.teecup.shop.repository.TeeRepository; 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
public class TeeRestController {
    private static final Logger logger = LogManager.getLogger(TeeRestController.class);
    @Autowired
    private TeeRepository teeRepo;

    @GetMapping("/rest/tee/{id}")
    public ResponseEntity<Tee> getTee(@PathVariable Long id) {
        return teeRepo.findById(id)
                .map(widget -> ResponseEntity.ok().body(widget))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rest/tees")
    //public ResponseEntity<Iterable<Widget>> getWidgets() {
    public Iterable<Tee> getWidgets() {
        return teeRepo.findAll();
    }

    @PostMapping("/rest/tee")
    public ResponseEntity<Tee> createTee(@RequestBody Tee tee) {
        logger.info("Received tee: name: " + tee.getName() + ", description: " + tee.getDescription());
        Tee newTee = teeRepo.save(tee);
        try {
            return ResponseEntity.created(new URI("/rest/tee/" + newTee.getId())).body(newTee);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/rest/tee/{id}")
    public ResponseEntity<Tee> updateTee(@RequestBody Tee tee, @PathVariable Long id) {
    	tee.setId(id);
        return ResponseEntity.ok().body(teeRepo.save(tee));
    }

    @PutMapping("/rest/proper/tee/{id}")
    public ResponseEntity<Tee> updateTeeProper(@RequestBody Tee tee, @PathVariable Long id, @RequestHeader("If-Match") Long ifMatch) {
        Optional<Tee> existingTee = teeRepo.findById(id);
        if (existingTee.isPresent()) {
            if (ifMatch == 7) {
                tee.setId(id);
                return ResponseEntity.ok().body(teeRepo.save(tee));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/rest/tee/{id}")
    public ResponseEntity deleteTee(@PathVariable Long id) {
    	teeRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
