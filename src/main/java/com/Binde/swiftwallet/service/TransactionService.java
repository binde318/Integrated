package com.Binde.swiftwallet.service;


import com.Binde.swiftwallet.dtos.request.DepositAccountRequestDto;
import com.Binde.swiftwallet.dtos.request.TransferFundRequestDto;
import com.Binde.swiftwallet.dtos.request.WithdrawFundRequestDto;
import com.Binde.swiftwallet.dtos.response.DepositResponseDto;
import com.Binde.swiftwallet.dtos.response.TransferResponseDto;
import com.Binde.swiftwallet.dtos.response.WithdrawFundResponseDto;

public interface TransactionService {
    DepositResponseDto depositFunds(DepositAccountRequestDto depositAccountRequestDto);
    TransferResponseDto transferFunds(TransferFundRequestDto transferFundRequestDto);
    WithdrawFundResponseDto withdrawFunds(WithdrawFundRequestDto withdrawFundRequestDto);
}
