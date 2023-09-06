package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Duty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface DutyRepository extends JpaRepository<Duty,Long> {
    @Query("select d from Duty d")
   Collection<Duty> findAllByDuties();
Duty findByName(String name);
}
