package br.com.matsutech.parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.matsutech.parkingcontrol.dtos.ParkingSpotDto;
import br.com.matsutech.parkingcontrol.models.ParkingSpot;
import br.com.matsutech.parkingcontrol.services.ParkingSpotService;

@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
@RequestMapping("/api/v1/parking-spot")
public class ParkingSpotController {

    @Autowired
    private ParkingSpotService service;

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        if (service.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use !");
        }
        if (service.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in user !");
        }
        if (service.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflict: Parking Spot alredy registred for this apartment/block!");
        }

        var parkingSpot = new ParkingSpot();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpot);
        parkingSpot.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveNewParkingSpot(parkingSpot));
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpot>> listAllParkingSpot(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findParkingSpotById(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpot> parkingSpot = service.findParkingSpotById(id);
        if (!parkingSpot.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpot.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpotById(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpot> parkingSpot = service.findParkingSpotById(id);
        if (!parkingSpot.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ParkingSpot not found");
        }
        service.deleteParkingSpotById(parkingSpot.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully ! ");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpotById(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        Optional<ParkingSpot> parkingSpotOptional = service.findParkingSpotById(id);
        if (!parkingSpotOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ParkingSpot not found");
        }
        var parkingSpot = parkingSpotOptional.get();

        BeanUtils.copyProperties(parkingSpotDto, parkingSpot);

        return ResponseEntity.status(HttpStatus.OK).body(service.saveNewParkingSpot(parkingSpot));
    }

}
