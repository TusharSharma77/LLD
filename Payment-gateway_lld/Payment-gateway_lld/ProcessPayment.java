public class ProcessPayment extends PaymentGateway {
    public ProcessPayment() {
        super(3);
    }
    public ProcessPayment(int maxRetries) {
        super(maxRetries);
    }
}
