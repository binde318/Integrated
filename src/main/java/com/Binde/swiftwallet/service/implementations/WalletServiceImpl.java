package com.Binde.swiftwallet.service.implementations;

import com.Binde.swiftwallet.exceptions.ResourceNotFoundException;
import com.Binde.swiftwallet.repository.UserRepository;
import com.Binde.swiftwallet.repository.WalletRepository;
import com.Binde.swiftwallet.dtos.request.ActivateAccountRequestDto;
import com.Binde.swiftwallet.dtos.response.FetchAccountResponseDto;
import com.Binde.swiftwallet.entity.User;
import com.Binde.swiftwallet.entity.Wallet;
import com.Binde.swiftwallet.service.WalletService;
import com.Binde.swiftwallet.utils.AppUtil;
import com.Binde.swiftwallet.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository accountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public FetchAccountResponseDto fetchAccount(long accountNumber) {

        Wallet account = accountRepository.getAccountByAccountNumber(accountNumber)
                .orElseThrow(
                        () -> {throw new ResourceNotFoundException("account not found");
                        }
                );
        return ModelMapperUtils.map(account,new FetchAccountResponseDto());
    }

    @Override
    public boolean activateAccount(ActivateAccountRequestDto activateAccountRequestDto) {
        Wallet loggedInUser = getLoggedInUserAccountDetails();
        loggedInUser.setActivated(true);
        loggedInUser.setPin(passwordEncoder.encode(activateAccountRequestDto.getPin()));
        accountRepository.save(loggedInUser);
        return true;
    }

    private boolean validateBalance(long receiverAccountNumber, BigDecimal amount) {
        Wallet account = accountRepository.getAccountByAccountNumber(receiverAccountNumber)
                .orElseThrow(
                        () -> {throw new ResourceNotFoundException("account number not found");
                        }
                );
        return account.getAccountBalance().compareTo(amount) >= 0;
    }

    public Wallet getLoggedInUserAccountDetails() {
        log.info("AccountServiceImpl getLoggedInUserAccountDetails- :: ");
        String loggedInUser = AppUtil.getPrincipal();
        log.info("AccountServiceImpl getLoggedInUserAccountDetails- logged In user :: [{}]", loggedInUser);
        User user =  userRepository.getUserByEmail(loggedInUser).orElseThrow(
                () -> {throw new ResourceNotFoundException("user not found");
                }
        );
        return accountRepository.findById(user.getId()).orElseThrow(
                () -> {throw new ResourceNotFoundException("account not found");
                }
        );
    }
}
