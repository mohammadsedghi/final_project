package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomDuplicateInfoException;
import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.customerDto.CustomerDtoEmail;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.ordersDto.*;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistResult;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyNameDto;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.mapper.*;
import com.example.finalproject_phase2.repository.OrdersRepository;
import com.example.finalproject_phase2.service.*;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.CustomRegex;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.aspectj.weaver.ast.Or;
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
            System.out.println("2222");
            Optional<Customer> customer = customerService.findByEmail(submitOrderDto.getCustomerEmail());
            Specialist specialist = specialistService.findByEmail(submitOrderDto.getSpecialistEmail());
            SubDuty subDuty = subDutyService.findById(submitOrderDto.getSubDutyId());
            System.out.println(true + "1");
            if (customer.isPresent() && specialist.getFirstName() != null && subDuty.getName() != null) {
                if (!customRegex.checkOneInputIsValid(submitOrderDto.getProposedPrice(), customRegex.getValidPrice())) {
                    throw new CustomException("input  for orders is invalid");
                }
                if (!checkValidation.isValid(submitOrderDto.getAddress())) {
                    throw new CustomException("input address for orders is invalid");
                }
                System.out.println(true + "2");
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
        System.out.println(true + "3");
        return new OrdersDto();
    }

    @Override
    public Collection<Orders> showOrdersToSpecialist(SubDutyNameDto subDutyNameDtoDto) {
        ;
        return ordersRepository.showOrdersToSpecialist(subDutyMapper.subDutyDtoToSubDuty(subDutyService.findByName(subDutyNameDtoDto.getName())), OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION, OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION);
    }

    @Override
    public Orders updateOrderToNextLevel(OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus) {
        ordersDtoWithOrdersStatus.getOrders().setOrderStatus(ordersDtoWithOrdersStatus.getOrderStatus());
        ordersDtoWithOrdersStatus.getOrders().setId(1l);
        ordersRepository.save(ordersDtoWithOrdersStatus.getOrders());
        return ordersDtoWithOrdersStatus.getOrders();
    }

    @Override
    public OrdersDto findOrdersWithThisCustomerAndSubDuty(OrdersDtoWithCustomerAndSubDuty ordersDtoWithCustomerAndSubDuty) {
        try {
            ordersRepository.findOrdersWithThisCustomerAndSubDuty(ordersDtoWithCustomerAndSubDuty.getCustomer(), ordersDtoWithCustomerAndSubDuty.getSubDuty(), OrderStatus.ORDER_DONE).ifPresent(
                    orders -> {
                        throw new CustomDuplicateInfoException("this customer submit order for this subDuty");
                    });

        } catch (CustomDuplicateInfoException cdi) {
            throw new CustomDuplicateInfoException("this customer submit order for this subDuty");
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

    public Specification<Orders> hasOrdersWithThisDuty(SubDuty subDuty) {
        return (orders, cq, cb) -> cb.equal(orders.get("subDuty"), subDuty);
    }
    public  Specification<Orders> countOrders(String email) {
        return(orders, cq, cb) -> cb.equal(orders.get("customer").get("email"), email);
    }
          @Override
           public Long numberOfOrders(String email ){
            return ordersRepository.count(countOrders(email));

        }
    public static Specification<Orders> ordersWithSubDutyAndCustomerEmail(
            SubDuty subDuty, String email, LocalDate localDateStart, LocalDate localDateEnd, OrderStatus status) {
        return (Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // Initialize with conjunction

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

    //    public static Specification<Orders> ordersWithSubDutyAndCustomerEmail(SubDuty subDuty, String email,LocalDate localDateStart,LocalDate localDateEnd,OrderStatus orderStatus) {
//        return (Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
//            Predicate subDutyPredicate = criteriaBuilder.equal(root.get("subDuty"), subDuty);
//            Predicate emailPredicate = criteriaBuilder.equal(root.get("customer").get("email"), email);
//            Predicate localDatePredicate = criteriaBuilder.between(root.get("DateOfWork"), localDateStart,localDateEnd);
//            Predicate orderStatusPredicate = criteriaBuilder.equal(root.get("orderStatus"), orderStatus);
//            return criteriaBuilder.and(subDutyPredicate, emailPredicate,localDatePredicate,orderStatusPredicate);
//        };
//    }
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
                    System.out.println("2222");
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
        ordersRepository.findAll(where(ordersWithSubDutyAndCustomerEmail(subDuty, email, localDateStart, localDateEnd, status)))
                .forEach(orders -> ordersList.add(new OrdersResult(
                        orders.getSpecialist().getLastName(),
                        orders.getCustomer().getLastName()
                        , orders.getDateOfWork()
                        , orders.getProposedPrice())));
        return ordersList;
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