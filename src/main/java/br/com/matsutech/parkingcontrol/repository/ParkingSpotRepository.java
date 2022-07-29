package br.com.matsutech.parkingcontrol.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.matsutech.parkingcontrol.model.ParkingSpot;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, UUID> {

    boolean existsByLicensePlateCar(String licensePlateCar);

    boolean existsByParkingSpotNumber(String parkingSpotNumber);

    boolean existsByApartmentAndBlock(String apartment, String block);
}
