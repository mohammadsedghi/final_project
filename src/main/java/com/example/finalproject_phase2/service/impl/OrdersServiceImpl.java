package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomDuplicateInfoException;
import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.customerDto.CustomerDtoEmail;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.ordersDto.*;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyNameDto;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.mapper.*;
import com.example.finalproject_phase2.repository.OrdersRepository;
import com.example.finalproject_phase2.service.*;
import com.example.finalproject_phase2.util.validation.CheckValidation;
import com.example.finalproject_phase2.util.validation.CustomRegex;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final CustomerMapper customerMapper;
    private final SubDutyMapper subDutyMapper;
    CheckValidation checkValidation = new CheckValidation();
    private final OrdersMapper ordersMapper;
    private final CustomerService customerService;
    private final SpecialistService specialistService;
    private final SubDutyService subDutyService;
    private final AddressService addressService;
    private final AddressMapper addressMapper;
    private final DutyMapper dutyMapper;
    private final DutyService dutyService;

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository, CustomerMapper customerMapper, SubDutyMapper subDutyMapper, OrdersMapper ordersMapper, CustomerService customerService, SpecialistService specialistService, SubDutyService subDutyService, AddressService addressService, AddressMapper addressMapper, DutyMapper dutyMapper, DutyService dutyService) {
        this.ordersRepository = ordersRepository;
        this.customerMapper = customerMapper;
        this.subDutyMapper = subDutyMapper;
        this.ordersMapper = ordersMapper;
        this.customerService = customerService;
        this.specialistService = specialistService;
        this.subDutyService = subDutyService;
        this.addressService = addressService;
        this.addressMapper = addressMapper;
        this.dutyMapper = dutyMapper;
        this.dutyService = dutyService;
    }

    @Override
    public OrdersDto submitOrder(SubmitOrderDto submitOrderDto) {
        try {
            CheckValidation checkValidation = new CheckValidation();
            CustomRegex customRegex = new CustomRegex();
            Optional<Customer> customer = customerService.findByEmail(submitOrderDto.getCustomerEmail());
            Specialist specialist = specialistService.findByEmail(submitOrderDto.getSpecialistEmail());
            SubDuty subDuty = subDutyService.findById(submitOrderDto.getSubDutyId());
            if (customer.isPresent() && specialist.getFirstName() != null && subDuty.getName() != null) {
                if (!customRegex.checkOneInputIsValid(submitOrderDto.getProposedPrice(), customRegex.getValidPrice())) {
                    throw new CustomException("input  for orders is invalid");
                }
                if (!checkValidation.isValid(submitOrderDto.getAddress())) {
                    throw new CustomException("input address for orders is invalid");
                }

                LocalDate date = LocalDate.of(submitOrderDto.getYear(), submitOrderDto.getMonth(), submitOrderDto.getDay());
//                   SubDuty subDuty = subDutyMapper.subDutyDtoToSubDuty(subDutyDto);
                System.out.println(subDuty.getId());
                Orders orders = Orders.builder()
                        .specialist(specialist)
                        .customer(customer.get())
                        .subDuty(subDuty)
                        .address(addressService.
                                createAddress(addressMapper.addressToAddressDto(submitOrderDto.getAddress())))
                        .description(submitOrderDto.getDescription())
                        .DateOfWork(LocalDate.now())
//                   .timeOfWork(LocalTime.parse(submitOrderDto.getTimeOfWork()))
                        .timeOfWork(LocalTime.now())
                        .proposedPrice(Double.parseDouble(submitOrderDto.getProposedPrice()))
                        .orderStatus(OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION)
                        .build();
                ordersRepository.save(orders);
                return ordersMapper.ordersToOrdersDto(orders);
            }

        } catch (CustomException ce) {
            return new OrdersDto();
        }
        return new OrdersDto();
    }

    @Override
    public Collection<Orders> showOrdersToSpecialist(SubDutyNameDto subDutyNameDtoDto) {
        return ordersRepository.showOrdersToSpecialist(subDutyMapper.subDutyDtoToSubDuty(subDutyService.findByName(subDutyNameDtoDto.getName())), OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION, OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION);
    }

    @Override
    public Orders updateOrderToNextLevel(OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus) {
        Optional<Orders> optionalOrders = findById(ordersDtoWithOrdersStatus.getOrdersId());
        try {
            if (optionalOrders.isPresent()){
            optionalOrders.get().setOrderStatus(ordersDtoWithOrdersStatus.getOrderStatus());
            ordersRepository.save( optionalOrders.get());
            }else throw new CustomException(" order with this id not found");
        }catch (CustomException ce){
            throw new CustomException(ce.getMessage());
        }
        return  optionalOrders.get();
    }

    @Override
    public OrdersDto findOrdersWithThisCustomerAndSubDuty(OrdersDtoWithCustomerAndSubDuty ordersDtoWithCustomerAndSubDuty) {
        try {
            Optional<Customer> customer = customerService.findByEmail(ordersDtoWithCustomerAndSubDuty.getCustomerEmail());
            SubDuty subDuty = subDutyService.findByNames(ordersDtoWithCustomerAndSubDuty.getSubDutyName());
            Collection<Orders> orders = ordersRepository.findOrdersWithThisCustomerAndSubDuty(customer.get(), subDuty, OrderStatus.ORDER_DONE);
           if (orders.size()>0){ throw new CustomDuplicateInfoException("this customer have open order for this subDuty");
           }
        } catch (CustomDuplicateInfoException cdi) {
            throw new CustomDuplicateInfoException(cdi.getMessage());
        }
        return new OrdersDto();
    }


    @Override
    public Collection<Orders> findOrdersInStatusWaitingForSpecialistSuggestion(CustomerDtoEmail customerDtoEmail) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customerDtoEmail.getEmail(),
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION));
    }

    @Override
    public Collection<Orders> findOrdersInStatusWaitingForSpecialistSelection(CustomerDtoEmail customerDtoEmail) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSelection(customerDtoEmail.getEmail(),
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION));
    }

    @Override
    public Collection<Orders> findOrdersInStatusWaitingForSpecialistToWorkplace(CustomerDtoEmail customerDtoEmail) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistToWorkplace(customerDtoEmail.getEmail(),
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE));
    }

    @Override
    public Collection<Orders> findOrdersInStatusStarted(CustomerDtoEmail customerDtoEmail) {
        return (ordersRepository.findOrdersInStatusStarted(customerDtoEmail.getEmail(),
                OrderStatus.ORDER_STARTED));
    }

    @Override
    public Collection<Orders> findOrdersInStatusPaid(CustomerDtoEmail customerDtoEmail) {
        return (ordersRepository.findOrdersInStatusPaid(customerDtoEmail.getEmail(),
                OrderStatus.ORDER_PAID));
    }

    @Override
    public Collection<Orders> findOrdersInStatusDone(CustomerDtoEmail customerDtoEmail) {
        return (ordersRepository.findOrdersInStatusDone(customerDtoEmail.getEmail(),
                OrderStatus.ORDER_DONE));
    }

    @Override
    public Optional<Orders> findById(Long id) {
        return ordersRepository.findById(id);
    }

    public  Specification<Orders> countCustomerOrders(String email) {
        return(orders, cq, cb) -> cb.equal(orders.get("customer").get("email"), email);
    }
    public  Specification<Orders> countSpecialistOrders(String email) {
        return(orders, cq, cb) -> cb.equal(orders.get("specialist").get("email"), email);
    }
          @Override
           public Long numberOfOrders(String email ,String userType){
        if (userType.equals("customer")){
            System.out.println(userType);
            return ordersRepository.count(countCustomerOrders(email));
        }
        if (userType.equals("specialist")){
            System.out.println(userType);
            return ordersRepository.count(countSpecialistOrders(email));
        }

       return 0l;
        }


    public  Specification<Orders> advanceSearchInOrders(
            SubDuty subDuty, String email, LocalDate localDateStart, LocalDate localDateEnd, OrderStatus status) {
        return (Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (subDuty != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("subDuty"), subDuty));
            }
            if (email != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("customer").get("email"), email));
            }
            if (localDateStart != null && localDateEnd != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("DateOfWork"), localDateStart, localDateEnd));
            }
            if (status != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("orderStatus"), status));
            }
            return predicate;
        };
    }
    @Override
    public List<OrdersResult> searchInDuty(OrdersAdvanceSearchParameter ordersAdvanceSearchParameter) {
        Set<DutyDto> allDuties = new HashSet<>();
        if (ordersAdvanceSearchParameter.getDutyName()!=null&&!ordersAdvanceSearchParameter.getDutyName().equals("")) {
            allDuties.add(dutyService.findByName(ordersAdvanceSearchParameter.getDutyName()));
            allDuties.forEach(dutyDto -> System.out.println(dutyDto.getName()));
        } else {
            allDuties = dutyService.findAllByDuties();
            allDuties.forEach(dutyDto -> System.out.println(dutyDto.getName()));
        }
        List<OrdersResult> ordersList = new ArrayList<>();
        for (DutyDto dutyDto : allDuties
        ) {
            Duty duty = dutyMapper.dutyDtoToDuty(dutyDto);
            for (SubDuty subDuty : duty.getSubDuties()) {
                System.out.println("subDuty   "+subDuty.getName());
                if (ordersAdvanceSearchParameter.getSubDutyName()!= null&&!ordersAdvanceSearchParameter.getSubDutyName().isEmpty()) {
                    if (ordersAdvanceSearchParameter.getSubDutyName().equals(subDuty.getName())){
                        ordersList=findAll(subDuty,
                                ordersAdvanceSearchParameter.getEmail(),
                                ordersAdvanceSearchParameter.getDateOfWorkStart(),
                                ordersAdvanceSearchParameter.getDateOfWorkEnd(),
                                ordersAdvanceSearchParameter.getOrderStatus());
                        break;
                    }
                }else {
                    ordersList.addAll(findAll(subDuty,
                            ordersAdvanceSearchParameter.getEmail(),
                            ordersAdvanceSearchParameter.getDateOfWorkStart(),
                            ordersAdvanceSearchParameter.getDateOfWorkEnd(),
                            ordersAdvanceSearchParameter.getOrderStatus()));
                }
            }
        }
        return ordersList;
    }

    public List<OrdersResult> findAll(SubDuty subDuty, String email, LocalDate localDateStart, LocalDate localDateEnd, OrderStatus status) {
        List<OrdersResult> ordersList = new ArrayList<>();
        ordersRepository.findAll(where(advanceSearchInOrders(subDuty, email, localDateStart, localDateEnd, status)))
                .forEach(orders -> ordersList.add(new OrdersResult(
                        orders.getSpecialist().getLastName(),
                        orders.getCustomer().getLastName()
                        , orders.getDateOfWork()
                        , orders.getProposedPrice())));
        return ordersList;
    }
    public Collection<Orders> findOrdersForCustomerInStatus(CustomerDtoEmail customerDtoEmail,OrderStatus orderStatus) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customerDtoEmail.getEmail(),
                orderStatus));
    }
    public Collection<Orders> findOrdersForSpecialistInStatus(CustomerDtoEmail customerDtoEmail,OrderStatus orderStatus) {
        return (ordersRepository.findOrdersInStatusForSpecialist(customerDtoEmail.getEmail(),
                orderStatus));
    }

    public List<OrderStatus>toListOrdersStatus(){
        return List.of(OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION,
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION,
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE,
                OrderStatus.ORDER_STARTED,
                OrderStatus.ORDER_PAID,
                OrderStatus.ORDER_DONE);
    }
    @Override
    public Map<OrderStatus,List<OrdersResult>> showHistoryOrders(CustomerDtoEmail customerDtoEmail){
        Map<OrderStatus,List<OrdersResult>> orders = new HashMap<>();
        for (OrderStatus orderStatus:toListOrdersStatus()
             ) {
            List<OrdersResult> ordersResults= new ArrayList<>();
            if( customerService.findByEmail(customerDtoEmail.getEmail()).isPresent()){
                findOrdersForCustomerInStatus(customerDtoEmail,orderStatus).forEach(
                    orders1 -> { ordersResults.add(new OrdersResult(orders1.getSpecialist().getLastName()
                            ,orders1.getCustomer().getLastName()+orders1.getOrderStatus().toString(),orders1.getDateOfWork(), orders1.getProposedPrice()));
                    });
            }else if(specialistService.findByEmailOptional(customerDtoEmail.getEmail()).isPresent()){
                findOrdersForSpecialistInStatus(customerDtoEmail,orderStatus).forEach(
                        orders1 -> { ordersResults.add(new OrdersResult(orders1.getSpecialist().getLastName()
                                ,orders1.getCustomer().getLastName()+orders1.getOrderStatus().toString(),orders1.getDateOfWork(), orders1.getProposedPrice()));
                        });
            }else throw new CustomException("user not found");
            orders.put(orderStatus, ordersResults);
        }
        return orders;
    }

}
//    @Override
//    public List<OrdersResult> searchInDuty(String dutyName ) {
//        DutyDto dutyDto = dutyService.findByName(dutyName);
//        List<OrdersResult> ordersList=new ArrayList<>();
//        Duty duty = dutyMapper.dutyDtoToDuty(dutyDto);
//        for (SubDuty subDuty:duty.getSubDuties()
//        ) {
//            ordersRepository.findAll(where(hasOrdersWithThisDuty(subDuty)))
//    .forEach(orders -> ordersList.add(new OrdersResult(
//            orders.getSpecialist().getLastName(),
//            orders.getCustomer().getLastName()
//            ,orders.getDateOfWork()
//            ,orders.getProposedPrice())));
//        }
//        return ordersList;
//    }
//}
 //  ordersRepository.findAll(where(ordersWithSubDutyAndCustomerEmail(subDuty,
//                                ordersAdvanceSearchParameter.getEmail(),
//                                ordersAdvanceSearchParameter.getDateOfWorkStart(),
//                                ordersAdvanceSearchParameter.getDateOfWorkEnd(),
//                                ordersAdvanceSearchParameter.getOrderStatus()
//                        )))
//                        .forEach(orders -> ordersList.add(new OrdersResult(
//                                orders.getSpecialist().getLastName(),
//                                orders.getCustomer().getLastName()
//                                , orders.getDateOfWork()
//                                , orders.getProposedPrice())));