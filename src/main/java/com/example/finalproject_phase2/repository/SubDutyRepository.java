package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.entity.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
@Repository
public interface SubDutyRepository extends JpaRepository<SubDuty,Long> {
    @Query("select sd from SubDuty sd where sd.duty=:duty")
    Collection<SubDuty> showSubDutyOfDuty(Duty duty);
    @Query("select sd from SubDuty sd where sd.name=:name ")
    Optional<SubDuty> isExistSubDuty(String name);
    Optional<SubDuty> findByName(String name);

}
