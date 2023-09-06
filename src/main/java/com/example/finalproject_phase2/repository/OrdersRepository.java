package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long>,JpaSpecificationExecutor<Orders> {
    @Query("select o from Orders o where o.subDuty = :subDuty and (o.orderStatus = :orderStatusWaitingForSuggest or o.orderStatus = :orderStatusWaitingForSelect)")
    Collection<Orders> showOrdersToSpecialist(SubDuty subDuty,OrderStatus orderStatusWaitingForSuggest,OrderStatus orderStatusWaitingForSelect );
    @Query("select o from Orders o where o.customer=:customer and o.subDuty=:subDuty and o.orderStatus !=:orderStatus")
    Optional<Orders> findOrdersWithThisCustomerAndSubDuty(Customer customer, SubDuty subDuty,OrderStatus orderStatus);
    @Query("select o from Orders o where o.customer.email=:email and o.orderStatus =:orderStatus")
    Collection<Orders> findOrdersInStatusWaitingForSpecialistSuggestion(String email , OrderStatus orderStatus);
    @Query("select o from Orders o where o.customer.email=:email and o.orderStatus =:orderStatus")
    Collection<Orders> findOrdersInStatusWaitingForSpecialistSelection(String email,OrderStatus orderStatus);
    @Query("select o from Orders o where o.customer.email=:email and o.orderStatus =:orderStatus")
    Collection<Orders> findOrdersInStatusWaitingForSpecialistToWorkplace(String email ,OrderStatus orderStatus);
    @Query("select o from Orders o where o.customer.email=:email and o.orderStatus =:orderStatus")
    Collection<Orders> findOrdersInStatusStarted(String email,OrderStatus orderStatus);
    @Query("select o from Orders o where o.customer.email=:email and o.orderStatus =:orderStatus")
    Collection<Orders> findOrdersInStatusPaid(String email ,OrderStatus orderStatus);
    @Query("select o from Orders o where o.customer.email=:email and o.orderStatus =:orderStatus")
    Collection<Orders> findOrdersInStatusDone(String email,OrderStatus orderStatus);
    Optional<Orders> findById(Long id);
}
