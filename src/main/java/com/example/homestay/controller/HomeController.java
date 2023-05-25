package com.example.homestay.controller;

import com.example.homestay.model.Homes;
import com.example.homestay.repository.HomeRepository;
import com.example.homestay.service.home.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/homes")
public class HomeController {
    @Autowired
    IHomeService homeService;
    @Autowired
    private HomeRepository homeRepository;
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
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Homes> updateStatusHome(@PathVariable Long id, @RequestBody Homes homes){
        Optional<Homes> homeOptional = homeService.findById(id);
        if (homeOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            homes.setStatus(homeOptional.get().getStatus());
            return new ResponseEntity<>(homeService.save(homes), HttpStatus.OK);
        }
    }




    @GetMapping("/search")
    public ResponseEntity<List<Object>> searchHomes(
            @RequestParam(value = "bedroom", required = false) Integer bedroom,
            @RequestParam(value = "bathroom", required = false) Integer bathroom,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "end_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "min_price", required = false) BigDecimal minPrice,
            @RequestParam(value = "max_price", required = false) BigDecimal maxPrice
    ) {
        List<Object> homes = homeService.searchHomes(bedroom, bathroom, address, startDate, endDate, minPrice, maxPrice);
        return ResponseEntity.ok(homes);
    }


}