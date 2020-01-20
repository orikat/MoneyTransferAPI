package webservices;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import com.revolut.managers.charging.DefaultChargingManager;
import com.revolut.models.Response;
import com.revolut.models.UserAccount;
import com.revolut.models.UserTransaction;
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
     
     UserAccount sender = new UserAccount(1,"", "", 11111, new BigDecimal(80000), "USD");
     UserAccount rec = new UserAccount(2,"", "", 22222,new BigDecimal(0), "USD");
     
     UserTransaction transaction = new UserTransaction(1, sender, rec, new BigDecimal(30000));
     ClientResponse response = resource().path("transactions/").type(MediaType.APPLICATION_XML).post(ClientResponse.class, transaction);
     Response localResponse = response.getEntity(Response.class);
     
     assertEquals(response.getStatus(), Status.OK);
     assertEquals(localResponse.getStatus(), Status.OK);
     assertEquals(localResponse.getMessage(), "success");
     Assert.assertTrue((localResponse.getInvoice() != null));
     
     BigDecimal commFeeValue = new BigDecimal(30000).multiply(DefaultChargingManager.getComissionFeePercentage());
     BigDecimal taxFeeValue = new BigDecimal(30000).multiply(DefaultChargingManager.getTaxFeePercentage());
     BigDecimal total = commFeeValue.add(taxFeeValue);

     assertEquals(localResponse.getInvoice().getId(), 1);
     assertEquals(localResponse.getInvoice().getRecieverTransferedAmount().intValue(), 30000);
     assertEquals(localResponse.getInvoice().getSenderTransferedAmount().intValue(), 30000);
     assertEquals(localResponse.getInvoice().getChargingFees().setScale(2,RoundingMode.FLOOR), total.setScale(2, RoundingMode.FLOOR));

     
     assertEquals(localResponse.getInvoice().getSenderAccount().getAccount().setScale(2, RoundingMode.FLOOR), new BigDecimal(50000).subtract(total).setScale(2, RoundingMode.FLOOR));
     assertEquals(localResponse.getInvoice().getSenderAccount().getCurrency(), "USD");
     
     assertEquals(localResponse.getInvoice().getRecieverAccount().getId(), 2);
     assertEquals(localResponse.getInvoice().getRecieverAccount().getAccount().intValue(), 30000);
     assertEquals(localResponse.getInvoice().getRecieverAccount().getCurrency(), "USD");
    }
    
    @Test
    public void testPost_notEnoughCredit() {
     
     UserAccount sender = new UserAccount(1,"", "", 11111, new BigDecimal(400), "USD");
     UserAccount rec = new UserAccount(2,"", "", 22222,new BigDecimal(0), "USD");
     
     UserTransaction transaction = new UserTransaction(1, sender, rec, new BigDecimal(30000));
     ClientResponse response = resource().path("transactions/").type(MediaType.APPLICATION_XML).post(ClientResponse.class, transaction);
     Response localResponse = response.getEntity(Response.class);
     assertEquals(localResponse.getStatus(), javax.ws.rs.core.Response.Status.CONFLICT.getStatusCode());
     Assert.assertTrue(localResponse.getMessage() != "success");
     
    }
}
