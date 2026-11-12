public class PaymentResponse {
    private PaymentStatus paymentStatus;
    private String transactionId;
    private String message;

    public PaymentResponse(PaymentStatus paymentStatus, String transactionId, String message) {
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.message = message;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PaymentResponse [status=" + paymentStatus + ", txnId=" + transactionId + ", message=" + message + "]";
    }
}
