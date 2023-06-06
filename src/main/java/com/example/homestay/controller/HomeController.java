package com.example.homestay.controller;

import com.example.homestay.model.DTO.HomeSearch;
import com.example.homestay.model.DTO.IncomeDTO;
import com.example.homestay.model.Homes;
import com.example.homestay.service.home.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/homes")
public class HomeController {
    @Autowired
    IHomeService homeService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<Iterable<Homes>> showAllHomes() {
        Iterable<Homes> homes = homeService.findAll();
        return new ResponseEntity<>(homes, HttpStatus.OK);
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

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Homes> updateStatusHome(@PathVariable Long id, @RequestBody Homes homes) {
        Optional<Homes> homeOptional = homeService.findById(id);
        if (homeOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            homes.setStatus(homeOptional.get().getStatus());
            return new ResponseEntity<>(homeService.save(homes), HttpStatus.OK);
        }
    }

    @PutMapping("/after-booking/{id}")
    public ResponseEntity<Homes> updateStatusAfterBooking(@PathVariable Long id) {
        Optional<Homes> homeOptional = homeService.findById(id);
        if (homeOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            homeOptional.get().setStatus(3);
            return new ResponseEntity<>(homeService.save(homeOptional.get()), HttpStatus.OK);
        }
    }

    @PutMapping("/after-bookings/{id}")
    public ResponseEntity<Homes> updateStatusAfterBookingDrum(@PathVariable Long id) {
        Optional<Homes> homeOptional = homeService.findById(id);
        if (homeOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            homeOptional.get().setStatus(1);
            return new ResponseEntity<>(homeService.save(homeOptional.get()), HttpStatus.OK);
        }
    }
    @PutMapping("/after-checkin/{id}")
    public ResponseEntity<Homes> updateStatusAfterBooking3(@PathVariable Long id) {
        Optional<Homes> homeOptional = homeService.findById(id);
        if (homeOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            homeOptional.get().setStatus(3);
            return new ResponseEntity<>(homeService.save(homeOptional.get()), HttpStatus.OK);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<HomeSearch>> searchHomes() {
        return new ResponseEntity<>(homeService.getAllSearchHomes(), HttpStatus.OK);
    }

    @GetMapping("/{id}/home-type")
    public ResponseEntity<List<Homes>> findByHomeType(@PathVariable Long id) {
        return new ResponseEntity<>(homeService.findHomeByHomeTypeId(id), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/income")
    public ResponseEntity<List<IncomeDTO>> getUserIncome(@PathVariable Long userId) {
        List<IncomeDTO> incomeList = homeService.getUserIncome(userId);
        return ResponseEntity.ok(incomeList);
    }

    @GetMapping(value = {"/get-avg"})
    public ResponseEntity<List<Map<String, Object>>> showAvg() {
        List<Map<String, Object>> homes = homeService.getHomesWithAverageRating();
        return new ResponseEntity<>(homes, HttpStatus.OK);
    }
}