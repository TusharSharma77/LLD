public class PaymentRequest {
    private String orderId;
    private double amount;
    private PaymentType paymentType;
    private String idempotencyKey;

    public PaymentRequest(String orderId, double amount, PaymentType paymentType, String idempotencyKey) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.idempotencyKey = (idempotencyKey != null && !idempotencyKey.isEmpty()) ? idempotencyKey : orderId;
    }

    public PaymentRequest(String orderId, double amount, PaymentType paymentType) {
        this(orderId, amount, paymentType, orderId);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
