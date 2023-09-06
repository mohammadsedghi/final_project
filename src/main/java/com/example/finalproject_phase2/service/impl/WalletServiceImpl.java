package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionIdDto;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
import com.example.finalproject_phase2.entity.Wallet;
import com.example.finalproject_phase2.repository.WalletRepository;
import com.example.finalproject_phase2.service.SpecialistSuggestionService;
import com.example.finalproject_phase2.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    public Wallet createWallet() {
        Wallet wallet = new Wallet(0d);
        walletRepository.save(wallet);
        return wallet;
    }


    public void withDraw(Customer customer, Specialist specialist,Double price) {
        Wallet customerWallet = customer.getWallet();
        Wallet specialistWallet=specialist.getWallet();
        Double customerBalance = customerWallet.getBalance();
        Double specialistBalance = specialistWallet.getBalance();
        Double calcCostOFOrder=(price*70)/100;
        customerBalance=customerBalance-price;
        specialistBalance=specialistBalance+calcCostOFOrder;
        customerWallet.setBalance(customerBalance);
        specialistWallet.setBalance(specialistBalance);
        walletRepository.save(customerWallet);
        walletRepository.save(specialistWallet);
    }
    public void withDrawOnline( Specialist specialist,Double price) {
        Wallet specialistWallet=specialist.getWallet();
        Double specialistBalance = specialistWallet.getBalance();
        Double calcCostOFOrder=(price*70)/100;
        specialistBalance=specialistBalance+calcCostOFOrder;
        specialistWallet.setBalance(specialistBalance);
        walletRepository.save(specialistWallet);
    }

    @Override
    public String payWithWallet(SpecialistSuggestion specialistSuggestion) {
        try {
            if (specialistSuggestion.getOrder().getCustomer().getWallet().getBalance()<
                    specialistSuggestion.getProposedPrice()){
                throw new CustomException("wallet have not enough balance");
            }
            withDraw(specialistSuggestion.getOrder().getCustomer(),
                    specialistSuggestion.getSpecialist(),
                    specialistSuggestion.getProposedPrice());
            return "transaction is success";
        }catch (CustomException ce){
            throw new CustomException(ce.getMessage());
        }
    }

    @Override
    public String payWithOnlinePayment(Specialist specialist,Double proposedPrice ) {
        try {
            withDrawOnline( specialist, proposedPrice);
            return "transaction is success";
        }catch (CustomException ce){
            throw new CustomException("transaction is failed");
        }
    }
    @Override
    public Double ShowBalance(Wallet wallet){
        return wallet.getBalance();
    }
}
