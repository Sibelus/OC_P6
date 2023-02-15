package com.openclassrooms.paymybuddy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeeService implements IFeeService {

    Logger logger = LoggerFactory.getLogger(FeeService.class);

    @Override
    public Float calculateFee(int amount) {
        Float fee = (float)(amount*5)/100;
        logger.debug("For {}, fees are equals to {}", amount, fee);
        return fee;
    }
}
