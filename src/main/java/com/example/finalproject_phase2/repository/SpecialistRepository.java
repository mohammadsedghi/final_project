package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.enumeration.SpecialistRegisterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
@Repository
public interface SpecialistRepository extends JpaRepository<Specialist,Long>, JpaSpecificationExecutor<Specialist> {
    Optional<Specialist> findByEmail(String email);
    Optional<Specialist> findByEmailAndPassword(String email,String password);
    @Query("select s from Specialist s where s.status=:specialistRegisterStatus")
    Collection<Specialist> showUnConfirmSpecialist(SpecialistRegisterStatus specialistRegisterStatus);
    @Query("select s from Specialist s where s.status=:specialistRegisterStatus")
    Collection<Specialist> showConfirmSpecialist(SpecialistRegisterStatus specialistRegisterStatus);

}
