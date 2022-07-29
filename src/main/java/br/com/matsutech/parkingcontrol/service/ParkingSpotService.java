package br.com.matsutech.parkingcontrol.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matsutech.parkingcontrol.model.ParkingSpot;
import br.com.matsutech.parkingcontrol.repository.ParkingSpotRepository;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository repository;

    @Transactional
    public ParkingSpot saveNewParkingSpot(ParkingSpot parkingSpot) {
        return repository.save(parkingSpot);
    }

    public List<ParkingSpot> findAll() {
        return repository.findAll();
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return repository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return repository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return repository.existsByApartmentAndBlock(apartment, block);
    }

    public Optional<ParkingSpot> findParkingSpotById(UUID id) {
        return repository.findById(id);
    }
    
    @Transactional
    public void deleteParkingSpotById(ParkingSpot parkingSpot) {
        repository.delete(parkingSpot);
    }
}
