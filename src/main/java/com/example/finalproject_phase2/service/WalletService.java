package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionIdDto;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
import com.example.finalproject_phase2.entity.Wallet;

public interface WalletService {
    Wallet createWallet();
    String payWithWallet(SpecialistSuggestion specialistSuggestion);
    String payWithOnlinePayment(Specialist specialist, Double proposedPrice );
    Double ShowBalance(Wallet wallet);
}
