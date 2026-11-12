public class PaymentMethodFactory {
    public static PaymentMethod getPaymentMethod(PaymentType type) {
        switch (type) {
            case CARD:
                return new CardPayment();
            case UPI:
                return new UpiPayment();
            case NETBANKING:
                return new NetBankingPayment();
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + type);
        }
    }
}
