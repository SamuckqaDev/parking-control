package br.com.matsutech.parkingcontrol.service;

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


    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return repository.existsByLicensePlateCar(licensePlateCar);
    }

  
    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return repository.existsByParkingSpotNumber(parkingSpotNumber);
    }


    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return repository.existsByApartmentAndBlock(apartment, block);
    }
}
