/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.managers.transaction;

import com.revolut.Exception.NoEnoughCreditException;
import com.revolut.models.Invoice;
import com.revolut.models.UserTransaction;

/**
 * Interface that force implementers to process different types of transaction 
 * **/
public interface TransactionManager {
   
    /**
     * @param UserTransaction: holds sender and receiver info with amount to be transferred
     * @return Invoice: final invoice that contains the details of the transaction done
     * @exception NoEnoughCreditException if sender credit is not enough to transfer amount
     * @exception Exception if unexpected error happened
     * **/
   public Invoice processTransaction(UserTransaction userTransaction) throws NoEnoughCreditException, Exception;

}
