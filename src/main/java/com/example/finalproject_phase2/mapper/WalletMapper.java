package com.example.finalproject_phase2.mapper;

import com.example.finalproject_phase2.dto.walletDto.WalletDto;
import com.example.finalproject_phase2.entity.Wallet;
import org.mapstruct.Mapper;

@Mapper
public interface WalletMapper {
    Wallet walletDtoToWallet(WalletDto walletDto);
    Wallet walletToWalletDto(Wallet wallet);
}
