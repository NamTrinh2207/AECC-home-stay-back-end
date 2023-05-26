package com.example.homestay.controller;

import com.example.homestay.model.HomeType;
import com.example.homestay.service.homeType.IHomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/user/hometypes")
public class HomeTypeController {
    @Autowired
    private IHomeTypeService homeTypeService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<Iterable<HomeType>> listHomeTypes() {
        Iterable<HomeType> homeTypes = homeTypeService.findAll();
        return new ResponseEntity<>(homeTypes, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<HomeType> createHomeType(@RequestBody HomeType homeType) {
        return new ResponseEntity<>(homeTypeService.save(homeType), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<HomeType> editHomeType(@PathVariable Long id, @RequestBody HomeType homeType) {
        Optional<HomeType> homeTypeOptional = homeTypeService.findById(id);
        if (homeTypeOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            homeType.setId(homeTypeOptional.get().getId());
            return new ResponseEntity<>(homeTypeService.save(homeType), HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHomeType(@PathVariable Long id) {
        homeTypeService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<HomeType> viewHomeType(@PathVariable Long id) {
        Optional<HomeType> homeTypeOptional = homeTypeService.findById(id);
        return homeTypeOptional.map(homeType -> new ResponseEntity<>(homeType, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
