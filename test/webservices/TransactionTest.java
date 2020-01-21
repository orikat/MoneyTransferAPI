/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package webservices;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import com.revolut.managers.UserManager;
import com.revolut.managers.charging.DefaultChargingManager;
import com.revolut.models.TransactionParam;
import com.revolut.models.TransactionResponse;
import com.revolut.models.UserAccount;
import com.revolut.webservices.TransactionResource;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.spi.container.servlet.WebComponent;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly2.GrizzlyTestContainerFactory;
import com.sun.messaging.jmq.io.Status;


public class TransactionTest extends JerseyTest {
    
    @Override
    public WebAppDescriptor configure() {        
        return new WebAppDescriptor.Builder()
                .initParam(WebComponent.RESOURCE_CONFIG_CLASS,
                    ClassNamesResourceConfig.class.getName())
                .initParam(
                    ClassNamesResourceConfig.PROPERTY_CLASSNAMES,
                    TransactionResource.class.getName() + ";").build();
    }
    
    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyTestContainerFactory();
    }
    
    @Test
    public void testPost() {
     
     UserAccount user = new UserAccount(1,"","", 11111, new BigDecimal(80000), "USD");
     UserAccount rec = new UserAccount(2,"", "", 22222,new BigDecimal(0), "USD");
     UserManager.addUserAccount(user);
     UserManager.addUserAccount(rec);
     
     TransactionParam transaction = new TransactionParam(1, 2, new BigDecimal(30000));
     ClientResponse response = resource().path("transactions/").type(MediaType.APPLICATION_XML).post(ClientResponse.class, transaction);
     TransactionResponse transactionResponse = response.getEntity(TransactionResponse.class);
     
     assertEquals( javax.ws.rs.core.Response.Status.OK.getStatusCode(), response.getStatus());
     assertEquals(transactionResponse.getStatus(), Status.OK);
     assertEquals(transactionResponse.getMessage(), "success");
     Assert.assertTrue((transactionResponse.getInvoice() != null));
     
     BigDecimal commFeeValue = new BigDecimal(30000).multiply(DefaultChargingManager.getComissionFeePercentage());
     BigDecimal taxFeeValue = new BigDecimal(30000).multiply(DefaultChargingManager.getTaxFeePercentage());
     BigDecimal total = commFeeValue.add(taxFeeValue);

     assertEquals(transactionResponse.getInvoice().getRecieverTransferedAmount().intValue(), 30000);
     assertEquals(transactionResponse.getInvoice().getSenderTransferedAmount().intValue(), 30000);
     assertEquals(transactionResponse.getInvoice().getChargingFees().setScale(2,RoundingMode.FLOOR), total.setScale(2, RoundingMode.FLOOR));

     
     assertEquals(transactionResponse.getInvoice().getSenderAccount().getAccountValue().setScale(2, RoundingMode.FLOOR), new BigDecimal(50000).subtract(total).setScale(2, RoundingMode.FLOOR));
     assertEquals(transactionResponse.getInvoice().getSenderAccount().getCurrency(), "USD");
     
     assertEquals(transactionResponse.getInvoice().getRecieverAccount().getId(), 2);
     assertEquals(transactionResponse.getInvoice().getRecieverAccount().getAccountValue().intValue(), 30000);
     assertEquals(transactionResponse.getInvoice().getRecieverAccount().getCurrency(), "USD");
    }
    
    @Test
    public void testPost_notEnoughCredit() {
     
     UserAccount sender = new UserAccount(1,"", "", 11111, new BigDecimal(400), "USD");
     UserAccount rec = new UserAccount(2,"", "", 22222,new BigDecimal(0), "USD");
     UserManager.addUserAccount(sender);
     UserManager.addUserAccount(rec);
     
     TransactionParam transaction = new TransactionParam(1, 2, new BigDecimal(30000));
     ClientResponse response = resource().path("transactions/").type(MediaType.APPLICATION_XML).post(ClientResponse.class, transaction);
     TransactionResponse localResponse = response.getEntity(TransactionResponse.class);
     assertEquals(localResponse.getStatus(), javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
     Assert.assertTrue(localResponse.getMessage() != "success");
     
    }
}
