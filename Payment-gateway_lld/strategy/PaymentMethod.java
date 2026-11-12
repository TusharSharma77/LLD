public interface PaymentMethod {
    PaymentResponse pay(PaymentRequest request);
}
