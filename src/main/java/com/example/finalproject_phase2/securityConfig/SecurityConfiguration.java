package com.example.finalproject_phase2.securityConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
   private final JwtAuthenticationFilter  jwtAuthFilter;
    private final AuthenticationProvider authenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http.csrf(AbstractHttpConfigurer::disable);
         http.authorizeHttpRequests(
               authorizeRequests -> {
                   authorizeRequests.requestMatchers("api/admin/authentication").permitAll()
                           .requestMatchers("api/admin/register").permitAll()
                           .requestMatchers("api/customer/authenticationCustomer").permitAll()
                           .requestMatchers("api/customer/registerCustomer").permitAll()
                           .requestMatchers("api/specialist/register").permitAll()
                           .requestMatchers("api/specialist/authentication").permitAll()
                           .requestMatchers("api/customer/wallet/payment").permitAll()
                           .requestMatchers("/wallet/send-data").permitAll()
                           .requestMatchers("api/customer/wallet/after").permitAll()
                           .requestMatchers("api/customer/wallet/endTime").permitAll()
                           .requestMatchers("api/customer/wallet/captcha").permitAll()
                           .requestMatchers("api/customer//wallet/selectSpecialistSuggestion").permitAll()
                           .requestMatchers("payment4.jpg").permitAll()
                           .requestMatchers("error.jpg").permitAll()
                           .requestMatchers("success.jpg").permitAll()
                           .requestMatchers("/**").permitAll()
                           .requestMatchers("api/customer/**").permitAll()
                           .requestMatchers("api/customer/activate").permitAll()
                           .requestMatchers("api/specialist/activate").permitAll()
                           .requestMatchers("api/specialist/activateMessage").permitAll()

                           .requestMatchers("api/customer/changePassword").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/deleteAddress").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/submitAddress").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/submitOrders").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersWithThisCustomerAndSubDuty").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/updateOrderToNextLevelStatus").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusWaitingForSpecialistSuggestion").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusWaitingForSpecialistSelection").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusWaitingForSpecialistToWorkplace").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/changeStatusOrderToStarted").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/changeStatusOrderToStarted").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/changeStatusOrderToDone").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusDone").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusPaid").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/submitCustomerComments").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findCustomerOrderSuggestionOnScore").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findCustomerOrderSuggestionOnPrice").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/wallet/payWithWallet").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/email/send").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/wallet/ShowBalance").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/history/orders").hasAuthority("CUSTOMER")

                           .requestMatchers("api/specialist/updateScore").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/showImage").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/setImage").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/showOrdersToSpecialist").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/showScore").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/submitSpecialSuggestion").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/findSuggestWithThisSpecialistAndOrder").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/changeSpecialistSelectedOfOrder").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/changePassword").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/changeStatusOrderToWaitingForSpecialistToWorkplace").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/wallet/ShowBalance").hasAuthority("SPECIALIST")


                           .requestMatchers("api/admin/duty/submit").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/duty/findAll").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/subDuty/addSubDuty").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/subDuty/editSubDutyPrice").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/subDuty/editSubDutyDescription").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/subDuty/findAll").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/searchSpecialist").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/searchCustomer").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/advanceSearch").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/numberOfOrders").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/searchCustomerAnotherWay").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/confirmByAdmin").hasAuthority("ADMIN").anyRequest().authenticated();

                   http.authenticationProvider(authenticationFilter)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

               });
        return http.build();
    }

}
