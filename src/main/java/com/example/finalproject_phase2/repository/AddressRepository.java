package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    @Query("select a from Address a where a=:address")
    Optional<Address> findAddressById(Address address);
    Optional<Address> findById(Long id);
}
