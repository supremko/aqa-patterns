import lombok.*;
import org.junit.jupiter.api.*;

public class DeliveryTest {
    @Test
    void shouldTestReserveDelivery() {
        val deliveryPage = new Delivery();
        val authInfo = Info.getAuthInfo();
        deliveryPage.checkNegativeDate(authInfo);
        deliveryPage.checkPositiveDate(authInfo);
    }
}
