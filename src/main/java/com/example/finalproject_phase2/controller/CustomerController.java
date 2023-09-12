package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.dto.customerDto.CustomerDtoEmail;
import com.example.finalproject_phase2.dto.ordersDto.*;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionIdDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionResult;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.StatusOrderSpecialistSuggestionDto;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.securityConfig.AuthenticationResponse;
import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.dto.customerCommentsDto.CustomerCommentsDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.service.*;
import com.example.finalproject_phase2.mapper.AddressMapper;
import com.example.finalproject_phase2.mapper.CustomerMapper;
import com.example.finalproject_phase2.mapper.OrdersMapper;
import com.example.finalproject_phase2.service.captcha.CaptchaService;
import com.example.finalproject_phase2.service.email.EmailRequest;
import com.example.finalproject_phase2.service.email.MailService;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.CustomRegex;
import com.example.finalproject_phase2.util.validation.DtoValidation;
import com.example.finalproject_phase2.util.validation.PaymentValidation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalTime;
import java.util.*;

//@RestController
@Controller
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final AddressService addressService;
    private final CustomerCommentsService customerCommentsService;
    private final OrdersService ordersService;
    private final SpecialistSuggestionService specialistSuggestionService;
    private final MailService mailService;
    private final WalletService walletService;
    private final AddressMapper addressMapper;
    private final CustomerMapper customerMapper;
    private final OrdersMapper ordersMapper;
    private String captchaText;
    private LocalTime localTime;
    private CustomRegex customRegex=new CustomRegex();
    private final SpecialistService specialistService;
    CaptchaService captchaService=new CaptchaService();
DtoValidation dtoValidation=new DtoValidation();
PaymentValidation paymentValidation=new PaymentValidation();
private Specialist specialist;
private Double proposedPrice;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper, AddressService addressService, CustomerCommentsService customerCommentsService, MailService mailService, AddressMapper addressMapper, OrdersService ordersService, SpecialistSuggestionService specialistSuggestionService, OrdersMapper ordersMapper, WalletService walletService, SpecialistService specialistService) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.addressService = addressService;
        this.customerCommentsService = customerCommentsService;
        this.mailService = mailService;
        this.addressMapper = addressMapper;
        this.ordersService = ordersService;
        this.specialistSuggestionService = specialistSuggestionService;
        this.ordersMapper = ordersMapper;
        this.walletService = walletService;
        this.specialistService = specialistService;
    }


    @PostMapping("/registerCustomer")
    public ResponseEntity<AuthenticationResponse> registerCustomer(@RequestBody @Valid CustomerDto customerDto
            , @RequestParam String userType) {
        System.out.println(customerMapper.customerDtoToCustomer(customerDto).getEmail());
        CheckValidation.userType = userType;
        System.out.println(userType);
        if (userType.equals("customer")) {
            return ResponseEntity.ok(customerService.register(customerMapper.customerDtoToCustomer(customerDto)));
        } else return new ResponseEntity<>(new AuthenticationResponse(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/authenticationCustomer")
    public ResponseEntity<AuthenticationResponse> loginCustomer(@RequestBody CustomerLoginDto customerLoginDto
            , @RequestParam String userType) {
        CheckValidation.userType = userType;
        System.out.println(customerLoginDto.getEmail());
        System.out.println(userType);
        if (userType.equals("customer")) {
            return ResponseEntity.ok(customerService.authenticate(customerLoginDto));
        } else return new ResponseEntity<>(new AuthenticationResponse(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody @Valid CustomerChangePasswordDto customerChangePasswordDto) {

        if (customerService.changePassword(customerChangePasswordDto.getEmail(), customerChangePasswordDto.getNewPassword())) {
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else throw new CustomNoResultException("password not changed");
    }

    @PostMapping("/deleteAddress")
    public ResponseEntity<AddressDto> deleteAddress(@RequestBody AddressDto addressDto) {

        AddressDto address = addressService.removeAddress(addressDto);

        if (address != null) return new ResponseEntity<>(address, HttpStatus.ACCEPTED);
        else
            throw new CustomException("address not found");
    }

    @PostMapping("/submitAddress")
    public ResponseEntity<AddressDto> submitAddress(@RequestBody AddressDto addressDto) {
        Address address = addressService.createAddress(addressDto);
        if (address != null)
            return new ResponseEntity<>(addressMapper.addressToAddressDto(address), HttpStatus.ACCEPTED);
        else
            throw new CustomException("address not saved");
    }
    @PostMapping("/submitOrders")
    public ResponseEntity<OrdersDto> submitOrders(@RequestBody  SubmitOrderDto submitOrderDto) {
        OrdersDto ordersDto = ordersService.submitOrder(submitOrderDto);
        if (ordersDto!=null){
            return new ResponseEntity<>(ordersDto, HttpStatus.ACCEPTED);
        }else  throw new CustomException("orders not saved");
    }
    @PostMapping("/findOrdersWithThisCustomerAndSubDuty")
    public ResponseEntity<OrdersDto> findOrdersWithThisCustomerAndSubDuty(@RequestBody OrdersDtoWithCustomerAndSubDuty ordersDtoWithCustomerAndSubDuty ) {
      dtoValidation.isValid(ordersDtoWithCustomerAndSubDuty);
        OrdersDto ordersDto = ordersService.findOrdersWithThisCustomerAndSubDuty(ordersDtoWithCustomerAndSubDuty);
            return new ResponseEntity<>(ordersDto, HttpStatus.ACCEPTED);
    }
    @PostMapping("/updateOrderToNextLevelStatus")
    public ResponseEntity<OrdersDto> updateOrderToNextLevelStatus(@RequestBody  OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus ) {
        dtoValidation.isValid(ordersDtoWithOrdersStatus);
        OrdersDto ordersDto = ordersMapper.ordersToOrdersDto(ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus));
        return new ResponseEntity<>(ordersDto, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusWaitingForSpecialistSuggestion")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusWaitingForSpecialistSuggestion(@RequestBody  CustomerDtoEmail customerDtoEmail ) {
        dtoValidation.isValid(customerDtoEmail);
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusWaitingForSpecialistSuggestion(customerDtoEmail);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
       if (ordersDtoCollection.size()==0){throw new CustomException("not order with this condition exist");
       }else return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusWaitingForSpecialistSelection")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusWaitingForSpecialistSelection(@RequestBody  CustomerDtoEmail customerDtoEmail ) {
        dtoValidation.isValid(customerDtoEmail);
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusWaitingForSpecialistSelection(customerDtoEmail);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        if (ordersDtoCollection.size()==0){
            throw new CustomException("not order with this condition exist");
        }else return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusWaitingForSpecialistToWorkplace")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusWaitingForSpecialistToWorkplace(@RequestBody  CustomerDtoEmail customerDtoEmail) {
        dtoValidation.isValid(customerDtoEmail);
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusWaitingForSpecialistToWorkplace(customerDtoEmail);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        if (ordersDtoCollection.size()==0){
            throw new CustomException("not order with this condition exist");
        }return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/changeStatusOrderToStarted")
    public ResponseEntity<Boolean> changeStatusOrderToStarted(@RequestBody  StatusOrderSpecialistSuggestionDto statusOrderSpecialistSuggestionDto  ) {
        dtoValidation.isValid(statusOrderSpecialistSuggestionDto);
        specialistSuggestionService.changeStatusOrderToStarted(statusOrderSpecialistSuggestionDto);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
    @PostMapping("/changeStatusOrderToDone")
    public ResponseEntity<Boolean> changeStatusOrderToDone(@RequestBody  OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus) {
        dtoValidation.isValid(ordersDtoWithOrdersStatus);
        specialistSuggestionService.changeStatusOrderToDone(ordersDtoWithOrdersStatus);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusStarted")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusStarted(@RequestBody  CustomerDtoEmail customerDtoEmail) {
        dtoValidation.isValid(customerDtoEmail);
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusStarted(customerDtoEmail);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        if (ordersDtoCollection.size()==0){
            throw new CustomException("not order with this condition exist");
        }return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusDone")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusDone(@RequestBody  CustomerDtoEmail customerDtoEmail ) {
        dtoValidation.isValid(customerDtoEmail);
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusDone(customerDtoEmail);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        if (ordersDtoCollection.size()==0){
            throw new CustomException("not order with this condition exist");
        } else return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusPaid")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusPaid(@RequestBody  CustomerDtoEmail customerDtoEmail ) {
        dtoValidation.isValid(customerDtoEmail);
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusPaid(customerDtoEmail);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        if (ordersDtoCollection.size()==0){
            throw new CustomException("not order with this condition exist");
        }else return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/submitCustomerComments")
    public ResponseEntity<Boolean> submitCustomerComments(@RequestBody  CustomerCommentsDto customerCommentsDto) {
        dtoValidation.isValid(customerCommentsDto);
        customerCommentsService.submitCustomerCommentsService(customerCommentsDto);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findCustomerOrderSuggestionOnScore")
    public ResponseEntity<List<SpecialistSuggestionResult>> findCustomerOrderSuggestionOnScoreOfSpecialist(@RequestBody CustomerDtoEmail customerDtoEmail ) {
        dtoValidation.isValid(customerDtoEmail);
        List<SpecialistSuggestionResult> customerOrderSuggestionOnScoreOfSpecialist = specialistSuggestionService.findCustomerOrderSuggestionOnScoreOfSpecialist(customerDtoEmail);
        return new ResponseEntity<>(customerOrderSuggestionOnScoreOfSpecialist, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findCustomerOrderSuggestionOnPrice")
    public ResponseEntity<List<SpecialistSuggestionResult>> findCustomerOrderSuggestionOnPrice(@RequestBody  CustomerDtoEmail customerDtoEmail ) {
        dtoValidation.isValid(customerDtoEmail);
        List<SpecialistSuggestionResult> customerOrderSuggestionOnPrice = specialistSuggestionService.findCustomerOrderSuggestionOnPrice(customerDtoEmail);
        return new ResponseEntity<>(customerOrderSuggestionOnPrice, HttpStatus.ACCEPTED);
    }
@PostMapping("/wallet/selectSpecialistSuggestion")
public String selectSpecialistSuggestion(@RequestBody SpecialistSuggestionIdDto specialistSuggestionIdDto){
    SpecialistSuggestion suggestion = specialistSuggestionService.findById(specialistSuggestionIdDto.getId());
     specialist=suggestion.getSpecialist();
     proposedPrice=suggestion.getProposedPrice();
     return payment();
    }
    @RequestMapping(value = "/wallet/payment", method = RequestMethod.GET)
    public String payment() {
        localTime=LocalTime.now();
        return "index";

    }

    @PostMapping("/wallet/send-data")
    @ResponseBody
    public String sendData(@RequestParam String cardNumber1, @RequestParam String cardNumber2
            , @RequestParam String cardNumber3, @RequestParam String cardNumber4, @RequestParam String cvv2,
                           @RequestParam String month, @RequestParam String year,
                           @RequestParam String captcha, @RequestParam String password,
                           Model model) {
        String response="";
        response=paymentValidation.isValidCard(cardNumber1,cardNumber2,cardNumber3,cardNumber4
        ,cvv2,month,year,captchaText,captcha,password);
        if (LocalTime.now().getMinute()-localTime.getMinute()>=5){
            response="false";
        }
        if (response.equals("true")){
            walletService.payWithOnlinePayment(specialist,proposedPrice);
        }
        model.addAttribute("response", response);
        return response;
    }
    @GetMapping("/wallet/after")
    public String afterPage() {
        return "afterPayment";
    }
    @GetMapping("/wallet/endTime")
    public String anotherPage() {
        return "endTime";
    }

    @GetMapping("/wallet/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String captchaText = captchaService.generateCaptchaText(6); // 6 characters
        this.captchaText=captchaText;
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaText);
       BufferedImage captchaImage =captchaService.generateCaptchaImage(captchaText);
        response.setContentType(MimeTypeUtils.IMAGE_PNG_VALUE);
        OutputStream out = response.getOutputStream();
        ImageIO.write(captchaImage, "png", out);
        out.close();
    }


    @PostMapping("/wallet/payWithWallet")
    public ResponseEntity<String> payWithWallet(@RequestBody SpecialistSuggestionIdDto specialistSuggestionIdDto) {
        System.out.println(specialistSuggestionIdDto.getId());
                  SpecialistSuggestion specialistSuggestion = specialistSuggestionService.findById(specialistSuggestionIdDto.getId());
        System.out.println(specialistSuggestion.getOrder().getCustomer().getFirstName());
        return new ResponseEntity<>( walletService.payWithWallet(specialistSuggestion), HttpStatus.ACCEPTED);
    }
    @PostMapping("/email/send")
    public ResponseEntity <Map<String,String>> sendEmail(@RequestBody EmailRequest emailRequest) {

        mailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Email sent successfully!");

        return ResponseEntity.ok(response);
    }
    @GetMapping("/activate")
    public String activateAccount(@RequestParam("token") String token,Model model) {
        String response;
        if (CheckValidation.memberTypeCustomer.getIsEnable()){
            response="you clicked before and can not permission to clicked it again";
            model.addAttribute("response",response);
            return "activationFailure"; // Redirect to a failure page
        }
        if (customerService.isAccountActivated(token)) {
            response="your welcome";
            model.addAttribute("response",response);
            return "activationSuccess"; // Redirect to a success page
        } else {
            response="some thing is wrong please try again";
            model.addAttribute("response",response);
            return "activationFailure"; // Redirect to a failure page
        }
    }
    @PostMapping("/wallet/ShowBalance")
    public ResponseEntity<Double> ShowBalance(@RequestBody @Valid CustomerDtoEmail customerDtoEmail){
        dtoValidation.isValid(customerDtoEmail);
        Optional<Customer> customer = customerService.findByEmail(customerDtoEmail.getEmail());
       return new ResponseEntity<>(walletService.ShowBalance(customer.get().getWallet()),HttpStatus.ACCEPTED);
    }

    @PostMapping("/history/orders")
    public ResponseEntity<Map<OrderStatus,List<OrdersResult>>> showHistoryOrders(@RequestBody  CustomerDtoEmail customerDtoEmail){
        dtoValidation.isValid(customerDtoEmail);
        return new ResponseEntity<>(ordersService.showHistoryOrders(customerDtoEmail),HttpStatus.ACCEPTED);
    }

  }