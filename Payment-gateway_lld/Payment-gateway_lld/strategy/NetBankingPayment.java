import java.util.UUID;

public class NetBankingPayment implements PaymentMethod {
    @Override
    public PaymentResponse pay(PaymentRequest request) {
        String txnId = "TXN_NB_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new PaymentResponse(
                PaymentStatus.SUCCESS,
                txnId,
                "Netbanking payment of INR " + request.getAmount() + " processed successfully for Order: " + request.getOrderId()
        );
    }
}
