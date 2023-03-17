package com.openclassrooms.paymybuddy.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FeeServiceTest {

    @Autowired
    private IFeeService iFeeService;

    @Test
    public void testCalculateFee() {
        //GIVEN
        int amount = 100;

        //WHEN
        Float fee = iFeeService.calculateFee(amount);

        //THEN
        Assertions.assertEquals(5, fee);
    }
}
