package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SpecialistSuggestionRepository extends JpaRepository<SpecialistSuggestion,Long> {
    @Query("select ss from SpecialistSuggestion ss where ss.specialist=:specialist and ss.order=:orders ")
    Optional<SpecialistSuggestion> findSuggestWithThisSpecialistAndOrder(Specialist specialist, Orders orders);
   @Query("select ss from SpecialistSuggestion ss where ss.order.customer.email=:email order by ss.proposedPrice")
    List<SpecialistSuggestion> findCustomerOrderSuggestionOnPrice(String email);
    @Query("select ss from SpecialistSuggestion ss where ss.order.customer.email=:email order by ss.specialist.score")
    List<SpecialistSuggestion> findCustomerOrderSuggestionOnScoreOfSpecialist(String email);
    Optional<SpecialistSuggestion> findById(Long id);
}
