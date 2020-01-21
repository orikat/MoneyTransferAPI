/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */
package com.revolut.webservices;

import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.revolut.Exception.CurrencyConversionException;
import com.revolut.Exception.NoEnoughCreditException;
import com.revolut.managers.UserManager;
import com.revolut.managers.transaction.MoneyTransferTransactionManager;
import com.revolut.managers.transaction.TransactionManager;
import com.revolut.models.Invoice;
import com.revolut.models.TransactionParam;
import com.revolut.models.TransactionResponse;
import com.revolut.models.UserAccount;
import com.revolut.models.UserTransaction;

/**
 * web resource to transfer money between 2 accounts
 **/

@Path("/transactions")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class TransactionResource {

    /**
     * Process money transfer transaction from sender to receiver
     * 
     * @param transactionInput
     *            object that holds sender account id, receiver account id & amount to be transfered
     * @return response object holding result message and status and the final
     *         invoice return status 409 if no enough credit in sender account
     *         return status 500 if unexpected exception happened
     **/
    @POST
    public TransactionResponse processTransaction(TransactionParam transactionParam) {
        TransactionResponse res = new TransactionResponse();
        UserAccount sender = UserManager.getUserAccount(transactionParam.getSenderID());
        UserAccount receiver = UserManager.getUserAccount(transactionParam.getReceiverID());
        if (sender == null || receiver == null || transactionParam.getAmount() == null) {
            res.setStatus(Status.BAD_REQUEST.getStatusCode());
            res.setMessage("missing/inavlid parameters");
            return res;
        }
        try {
            UserTransaction transaction = new UserTransaction(new Random().nextLong(), sender, receiver,
                    transactionParam.getAmount());
            TransactionManager transactionManager = new MoneyTransferTransactionManager();
            Invoice invoice = transactionManager.processTransaction(transaction);
            res.setMessage("success");
            res.setStatus(Status.OK.getStatusCode());
            res.setInvoice(invoice);

        } catch (CurrencyConversionException e) {
            // do anything
            res.setMessage(e.getMessage());
            res.setStatus(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        } catch (NoEnoughCreditException e) {
            // do anything
            res.setMessage(e.getMessage());
            res.setStatus(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        } catch (Exception e) {
            res.setMessage(e.getMessage());
            res.setStatus(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
        return res;
    }
}
