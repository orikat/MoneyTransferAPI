package com.revolut.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.revolut.Exception.NoEnoughCreditException;
import com.revolut.managers.TransactionManager;
import com.revolut.models.Invoice;
import com.revolut.models.Response;
import com.revolut.models.UserTransaction;

@Path("/transactions")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class TransactionResource {

    @POST
    public Response createTransaction(UserTransaction transaction) {
        Response res = new Response();
        try {
        TransactionManager transactionManager = new TransactionManager(); 
        Invoice invoice = transactionManager.processTransaction(transaction);
        res.setMessage("success");
        res.setStatus(Status.OK.getStatusCode());
        res.setInvoice(invoice);
        
        } catch (NoEnoughCreditException e) {
            res.setMessage(e.getMessage());
            res.setStatus(javax.ws.rs.core.Response.Status.CONFLICT.getStatusCode());
            
        } catch (Exception e) {
            res.setMessage(e.getMessage());
            res.setStatus(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
        return res;
    }
}
