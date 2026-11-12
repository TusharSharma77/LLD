import java.time.LocalDateTime;

public class Transaction {
    private final String transactionId;
    private final String orderId;
    private final double amount;
    private final PaymentType paymentType;
    private final PaymentStatus status;
    private final LocalDateTime timestamp;

    public Transaction(String transactionId, String orderId, double amount, PaymentType paymentType, PaymentStatus status) {
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction [id=" + transactionId + ", orderId=" + orderId + ", amount=" + amount +
                ", type=" + paymentType + ", status=" + status + ", timestamp=" + timestamp + "]";
    }
}
