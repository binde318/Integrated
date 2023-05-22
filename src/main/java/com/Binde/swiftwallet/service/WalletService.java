package com.Binde.swiftwallet.service;


import com.Binde.swiftwallet.entity.Wallet;
import com.Binde.swiftwallet.dtos.request.ActivateAccountRequestDto;
import com.Binde.swiftwallet.dtos.response.FetchAccountResponseDto;

public interface WalletService {
    FetchAccountResponseDto fetchAccount(long accountNumber);
    boolean activateAccount(ActivateAccountRequestDto activateAccountRequestDto);
    Wallet getLoggedInUserAccountDetails();

}
