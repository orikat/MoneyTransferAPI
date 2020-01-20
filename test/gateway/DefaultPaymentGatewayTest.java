package gateway;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.revolut.gateways.DefaultPaymentGateWay;
import com.revolut.gateways.PaymentGateway;
import com.revolut.gateways.PaymentGatewayStatus;
import com.revolut.models.PaymentGatewayTransaction;
import com.revolut.models.UserAccount;
import com.revolut.models.UserTransaction;

public class DefaultPaymentGatewayTest {
    private BigDecimal senderCurrentAmount = new BigDecimal(80000);
    private BigDecimal recCurrentAmount = new BigDecimal(0);
    private BigDecimal transferedAmount = new BigDecimal(1000);
    
    @Test
    public void transferTest() {
        UserAccount sender = new UserAccount(1L, "orikat", "mk.orikat@gmail.com", 111000L, senderCurrentAmount, "USD");
        UserAccount receiver = new UserAccount(2L, "orikat", "mk.orikat@gmail.com", 122000L, recCurrentAmount, "USD");
        
        UserTransaction transaction = new UserTransaction(1, sender, receiver, null);
        PaymentGatewayTransaction paymentTransaction = new PaymentGatewayTransaction(transaction, transferedAmount, transferedAmount);
        PaymentGateway gateway = new DefaultPaymentGateWay();
        
        int status  = gateway.transfer(paymentTransaction);
        Assert.assertEquals(status, PaymentGatewayStatus.SUCCESSEFUL.getValue());
        Assert.assertEquals(paymentTransaction.getUserTransaction().getSender().getAccount(), senderCurrentAmount.subtract(transferedAmount));
        Assert.assertEquals(paymentTransaction.getUserTransaction().getReciever().getAccount(), recCurrentAmount.add(transferedAmount));
    }
}
