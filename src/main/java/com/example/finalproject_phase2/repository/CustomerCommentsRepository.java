package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.CustomerComments;
import com.example.finalproject_phase2.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerCommentsRepository extends JpaRepository<CustomerComments,Long> {
    @Query("SELECT COUNT(cc) FROM CustomerComments  cc WHERE cc.orders.specialist = :specialist")
    Integer findNumberOFCustomerCommentsThatSpecialistIsExist(Specialist specialist);
    Optional<CustomerComments> findById(Long id);
    @Query("SELECT cc FROM CustomerComments  cc WHERE cc.orders.specialist = :specialist")
    List<CustomerComments> findCustomerCommentsByThisSpecialistIsExist(Specialist specialist);
}
