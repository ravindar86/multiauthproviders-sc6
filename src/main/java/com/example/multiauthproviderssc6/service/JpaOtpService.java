package com.example.multiauthproviderssc6.service;

import com.example.multiauthproviderssc6.entities.Otp;
import com.example.multiauthproviderssc6.repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class JpaOtpService {

    @Autowired
    private OtpRepository otpRepository;

    public Optional<Otp> getOtpByUsername(String username){
        return otpRepository.findOtpByUsername(username);
    }

    public void saveOtp(Otp otp){
        otpRepository.save(otp);
    }

    public void removeOtp() {
        otpRepository.deleteAll();
    }
}
