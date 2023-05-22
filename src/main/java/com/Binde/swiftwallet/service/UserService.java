package com.Binde.swiftwallet.service;


import com.Binde.swiftwallet.dtos.request.UpdateUserRequestDto;
import com.Binde.swiftwallet.dtos.request.UserRegistrationRequestDto;
import com.Binde.swiftwallet.dtos.response.RegistrationResponseDto;
import com.Binde.swiftwallet.entity.User;

public interface UserService {
    RegistrationResponseDto registerUser(UserRegistrationRequestDto registrationRequestDto);
    User getLoggedInUser();
    void updateUser(UpdateUserRequestDto updateUserDto, String id);
    String verifyAccount(String token);
}
