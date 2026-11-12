import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentGateway {
    private final Map<String, PaymentResponse> idempotencyStore = new ConcurrentHashMap<>();
    private final List<Transaction> transactionHistory = Collections.synchronizedList(new ArrayList<>());
    private final int maxRetries;

    public PaymentGateway() {
        this(3); // default 3 retries
    }

    public PaymentGateway(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public PaymentResponse processPayment(PaymentRequest request) {
        String key = request.getIdempotencyKey();

        // 1. Idempotency Check: Prevent duplicate charge
        if (idempotencyStore.containsKey(key)) {
            System.out.println("[Idempotency] Duplicate request detected for Key: " + key
                    + ". Returning cached response without re-processing.");
            return idempotencyStore.get(key);
        }

        // 2. Resolve Strategy via Factory
        PaymentMethod paymentMethod = PaymentMethodFactory.getPaymentMethod(request.getPaymentType());

        // 3. Execution with Retries
        PaymentResponse response = null;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                System.out.println("[PaymentGateway] Processing attempt " + attempt + " of " + maxRetries
                        + " for Order: " + request.getOrderId() + " via " + request.getPaymentType());

                response = paymentMethod.pay(request);

                if (response != null && response.getPaymentStatus() == PaymentStatus.SUCCESS) {
                    System.out.println("[PaymentGateway] Payment succeeded on attempt " + attempt);
                    break;
                }
            } catch (Exception e) {
                System.out.println("[PaymentGateway] Attempt " + attempt + " failed due to: " + e.getMessage());
                if (attempt == maxRetries) {
                    response = new PaymentResponse(PaymentStatus.FAILED, null, "Payment failed after " + maxRetries + " attempts: " + e.getMessage());
                }
            }
        }

        // 4. Record Successful Transaction & Cache for Idempotency
        if (response != null) {
            if (response.getPaymentStatus() == PaymentStatus.SUCCESS) {
                idempotencyStore.put(key, response);
                transactionHistory.add(new Transaction(
                        response.getTransactionId(),
                        request.getOrderId(),
                        request.getAmount(),
                        request.getPaymentType(),
                        response.getPaymentStatus()
                ));
            }
        }

        return response;
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}
