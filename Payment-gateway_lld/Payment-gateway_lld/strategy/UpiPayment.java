import java.util.UUID;

public class UpiPayment implements PaymentMethod {
    @Override
    public PaymentResponse pay(PaymentRequest request) {
        String txnId = "TXN_UPI_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new PaymentResponse(
                PaymentStatus.SUCCESS,
                txnId,
                "UPI payment of INR " + request.getAmount() + " processed successfully for Order: " + request.getOrderId()
        );
    }
}
