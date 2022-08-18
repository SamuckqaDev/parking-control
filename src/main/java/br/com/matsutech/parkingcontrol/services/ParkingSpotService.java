package br.com.matsutech.parkingcontrol.services;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.matsutech.parkingcontrol.models.ParkingSpot;
import br.com.matsutech.parkingcontrol.repositories.ParkingSpotRepository;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository repository;

    @Transactional
    public ParkingSpot saveNewParkingSpot(ParkingSpot parkingSpot) {
        return repository.save(parkingSpot);
    }

    public Page<ParkingSpot> findAllPage(Pageable pageable) {
        return repository.findAll(pageable);
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
