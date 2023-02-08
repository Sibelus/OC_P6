package com.openclassrooms.paymybuddy.service;

import org.springframework.stereotype.Service;

@Service
public class FeeService implements IFeeService {
    @Override
    public Float calculateFee(int amount) {
        Float fee = (float)(amount*5)/100;
        return fee;
    }
}
