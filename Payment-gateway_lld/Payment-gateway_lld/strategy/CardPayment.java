import java.util.UUID;

public class CardPayment implements PaymentMethod {
    @Override
    public PaymentResponse pay(PaymentRequest request) {
        String txnId = "TXN_CARD_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new PaymentResponse(
                PaymentStatus.SUCCESS,
                txnId,
                "Card payment of INR " + request.getAmount() + " processed successfully for Order: " + request.getOrderId()
        );
    }
}
