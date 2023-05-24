package com.example.homestay.controller;

import com.example.homestay.model.Homes;
import com.example.homestay.service.home.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/homes")
public class HomeController {
    @Autowired
    IHomeService homeService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<Page<Homes>> showAllHomes(@RequestParam(defaultValue = "0") int page) {
        PageRequest pageable = PageRequest.of(page, 6);
        return new ResponseEntity<>(homeService.findAll(pageable), HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<Homes> createHome(@RequestBody Homes homes) {
        return new ResponseEntity<>(homeService.save(homes), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Homes> deleteHome(@PathVariable Long id) {
        Optional<Homes> homes = homeService.findById(id);
        if (homes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        homeService.remove(id);
        return new ResponseEntity<>(homes.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Homes> editHome(@PathVariable Long id, @RequestBody Homes homes) {
        Optional<Homes> homesOptional = homeService.findById(id);
        if (homesOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        homes.setId(homesOptional.get().getId());
        return new ResponseEntity<>(homeService.save(homes), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Homes> getHome(@PathVariable Long id) {
        Optional<Homes> homesOptional = homeService.findById(id);
        return homesOptional.map(homes
                -> new ResponseEntity<>(homes, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/top5")
    public ResponseEntity<List<Homes>> viewTop5(@RequestParam(value = "limit", defaultValue = "5") int limit){
        List<Homes> homesList = homeService.findTop5(limit);
        return new ResponseEntity<>(homesList, HttpStatus.OK);
    }
}

