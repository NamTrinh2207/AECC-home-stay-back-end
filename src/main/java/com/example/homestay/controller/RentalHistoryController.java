package com.example.homestay.controller;

import com.example.homestay.model.RentalHistory;
import com.example.homestay.service.rentalHistory.IRentalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/history")
public class RentalHistoryController {
    @Autowired
    private IRentalHistoryService rentalHistoryService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<Iterable<RentalHistory>> listRentals() {
        Iterable<RentalHistory> rentalHistories = rentalHistoryService.findAll();
        return new ResponseEntity<>(rentalHistories, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<RentalHistory> writeRentalHistory(@RequestBody RentalHistory rentalHistory) {
        return new ResponseEntity<>(rentalHistoryService.save(rentalHistory), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editHistory(@PathVariable Long id, @RequestBody RentalHistory rentalHistory) {
        Optional<RentalHistory> rentalHistoryOptional = rentalHistoryService.findById(id);
        if (rentalHistoryOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            rentalHistory.setId(rentalHistoryOptional.get().getId());
            return new ResponseEntity<>(rentalHistoryService.save(rentalHistory), HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHistory(@PathVariable Long id) {
        rentalHistoryService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalHistory> viewHistory(@PathVariable Long id) {
        Optional<RentalHistory> rentalHistoryOptional = rentalHistoryService.findById(id);
        return rentalHistoryOptional
                .map(rentalHistory -> new ResponseEntity<>(rentalHistory, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
