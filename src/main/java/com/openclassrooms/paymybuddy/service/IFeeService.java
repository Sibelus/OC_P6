package com.openclassrooms.paymybuddy.service;

import org.springframework.stereotype.Service;

@Service
public interface IFeeService {
    Float calculateFee(int amount);
}
