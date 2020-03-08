package com.oe.ioc.service;

import java.math.BigDecimal;

public interface ITransferService {

   void transfer(String accountno1, String accountno2, BigDecimal money);
}
