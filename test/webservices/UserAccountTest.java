package webservices;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.revolut.models.Response;
import com.revolut.models.UserAccount;
import com.revolut.webservices.UserAccountResource;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.spi.container.servlet.WebComponent;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly2.GrizzlyTestContainerFactory;
import com.sun.messaging.jmq.io.Status;

public class UserAccountTest extends JerseyTest {

    @Override
    public WebAppDescriptor configure() {        
        return new WebAppDescriptor.Builder()
                .initParam(WebComponent.RESOURCE_CONFIG_CLASS,
                    ClassNamesResourceConfig.class.getName())
                .initParam(
                    ClassNamesResourceConfig.PROPERTY_CLASSNAMES,
                    UserAccountResource.class.getName() + ";").build();
    }
    
    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyTestContainerFactory();
    }
    
    @Test
    public void testCreate() {
        UserAccount user = new UserAccount(1,"","", 11111, new BigDecimal(8000), "USD");
        ClientResponse response = resource().path("users/").type(MediaType.APPLICATION_XML).post(ClientResponse.class, user);
        Response localResponse = response.getEntity(Response.class);
        assertEquals(response.getStatus(), Status.OK);
        assertEquals(localResponse.getMessage(), "success");
        assertEquals(localResponse.getStatus(), Status.OK);
    }
    
    @Test
    public void testGet() {
        long userID = 1;
        long accountNumber = 11111;
        String currency = "USD";
        BigDecimal accountValue = new BigDecimal(8000);
        
        UserAccount user1 = new UserAccount(userID,"","", accountNumber, accountValue, currency);
        resource().path("users/").type(MediaType.APPLICATION_XML).post(ClientResponse.class, user1);
        
        ClientResponse response = resource().path("users/1").type(MediaType.APPLICATION_XML).get(ClientResponse.class);
        UserAccount localResponse = response.getEntity(UserAccount.class);
        
        assertEquals(response.getStatus(), Status.OK);
        assertEquals(localResponse.getId(), userID);
        assertEquals(localResponse.getAccountNumber(), accountNumber);
        assertEquals(localResponse.getAccount(), accountValue);
        assertEquals(localResponse.getCurrency(), currency);
    }
    
    @Test
    public void testGetAll() {
        long userID = 1;
        long accountNumber = 11111;
        String currency = "USD";
        BigDecimal accountValue = new BigDecimal(8000);
        
        UserAccount user1 = new UserAccount(userID,"","", accountNumber, accountValue, currency);
        resource().path("users/").type(MediaType.APPLICATION_XML).post(ClientResponse.class, user1);
        UserAccount user2 = new UserAccount(userID+1,"", "", accountNumber+1, accountValue.add(new BigDecimal(1)), currency);
        resource().path("users/").type(MediaType.APPLICATION_XML).post(ClientResponse.class, user2);
        
        ClientResponse response = resource().path("users/").type(MediaType.APPLICATION_XML).get(ClientResponse.class);
        List<UserAccount> localResponse = response.getEntity(new GenericType<List<UserAccount>>(){});
        
        assertEquals(response.getStatus(), Status.OK);
        assertEquals(localResponse.size(), 2);
        
        // Add user with same id will update existing
        UserAccount user3 = new UserAccount(userID,"","", accountNumber, accountValue, currency);
        resource().path("users/").type(MediaType.APPLICATION_XML).post(ClientResponse.class, user3);
        
        ClientResponse response2 = resource().path("users/").type(MediaType.APPLICATION_XML).get(ClientResponse.class);
        List<UserAccount> localResponse2 = response2.getEntity(new GenericType<List<UserAccount>>(){});
        
        assertEquals(response2.getStatus(), Status.OK);
        assertEquals(localResponse2.size(), 2);
    }
}
